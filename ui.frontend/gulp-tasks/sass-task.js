'use strict';
const gulp = require('gulp');
const sass = require('gulp-sass');
const gulpStylelint = require('gulp-stylelint');
const config = require('../clientlib.config');

sass.compiler = require('node-sass');

const sourcePath = config.sourcePath;
const destinationPath = config.destinationPath;

const srcCSS = sourcePath.css;
const distCSS = destinationPath.css;

const dependenciesCSS = config.dependenciesPath.css;
const baseCSS = config.basePath.css;

module.exports = () => {
  if (process.env.NODE_ENV === 'production') {
    // Building components for AEM Solutions
    gulp.src(dependenciesCSS.src)
      .pipe(sass({
        outputStyle: 'expanded'
      }).on('error', sass.logError))
      .pipe(gulp.dest(dependenciesCSS.dest));

    baseCSS.forEach(baseCSSObj => {
      gulp.src(baseCSSObj.src)
        .pipe(sass({
          outputStyle: 'expanded'
        }).on('error', sass.logError))
        .pipe(gulp.dest(baseCSSObj.dest));
    });
    return true;
  } else {
    return gulp.src(srcCSS)
      .pipe(gulpStylelint({
        reporters: [
          { formatter: 'string', console: true }
        ]
      }))
      .pipe(sass({
        outputStyle: 'expanded'
      }).on('error', sass.logError))
      .pipe(gulp.dest(distCSS));
  }
}