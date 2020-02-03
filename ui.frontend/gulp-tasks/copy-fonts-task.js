'use strict';
const gulp = require('gulp');
const config = require('../clientlib.config');

const sourcePath = config.sourcePath;
const destinationPath = config.destinationPath;
const srcFonts = sourcePath.font;

module.exports = () => {
  let distFonts = destinationPath.font;
  if (process.env.NODE_ENV === 'production') {
    distFonts = config.dependenciesPath.font;
  }
  gulp.src(srcFonts, {
    allowEmpty: true
  })
    .pipe(gulp.dest(distFonts));
};