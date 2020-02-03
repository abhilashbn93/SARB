'use strict';
const gulp = require('gulp');
var gpUtil = require('gulp-util');
const gpIf = require('gulp-if');
const gpConcat = require('gulp-concat');
const babel = require('gulp-babel');
const gpUglify = require('gulp-uglify');
const config = require('../clientlib.config');

const sourcePath = config.sourcePath;
const destinationPath = config.destinationPath;
const srcJSPath = sourcePath.js;
const distJSPath = destinationPath.js;

const srcVendorJS = srcJSPath.vendorLibs;
const srcComponentJS = srcJSPath.components;
const autoBinderJS = srcJSPath.autoBinderJs;

const dependenciesJS = config.dependenciesPath.js;
const baseJS = config.basePath.js;

module.exports = () => {
  let uglifyCondition = false;
  if (process.env.NODE_ENV === 'production') {
    uglifyCondition = false; //Make it true if want to set uglification for prod builds

    gulp.src(dependenciesJS.src, {
      allowEmpty: true
    })
      .pipe(gpIf(true, gpUglify()))
      .on('error', function (err) {
        gpUtil.log(gpUtil.colors.red('[Error]'), err.toString());
      })
      .pipe(gulp.dest(dependenciesJS.dest));

    baseJS.forEach(baseJSObj => {
      gulp.src(baseJSObj.src, {
        allowEmpty: true
      })
        .pipe(babel({
          presets: ['@babel/preset-env']
        }))
        .pipe(gpIf(uglifyCondition, gpUglify()))
        .on('error', function (err) {
          gpUtil.log(gpUtil.colors.red('[Error]'), err.toString());
        })
        .pipe(gulp.dest(baseJSObj.dest));
    });
    return true;
  } else {
    // Building files for local FE repo
    gulp.src(srcVendorJS, {
      allowEmpty: true
    })
      .pipe(gpIf(uglifyCondition, gpUglify()))
      .on('error', function (err) {
        gpUtil.log(gpUtil.colors.red('[Error]'), err.toString());
      })
      .pipe(gpConcat('vendor-libs.js'))
      .pipe(gulp.dest(distJSPath));

    // Building custom JS files
    gulp.src([srcComponentJS, '!' + autoBinderJS])
      .pipe(babel({
        presets: ['@babel/preset-env'],
        compact: false
      }))
      .pipe(gpIf(uglifyCondition, gpUglify()))
      .on('error', function (err) {
        gpUtil.log(gpUtil.colors.red('[Error]'), err.toString());
      })
      .pipe(gpConcat('app.js'))
      .pipe(gulp.dest(distJSPath));

    gulp.src(autoBinderJS)
      .pipe(babel({
        presets: ['@babel/preset-env'],
        compact: false
      }))
      .pipe(gulp.dest(distJSPath));

    return true;
  }
}