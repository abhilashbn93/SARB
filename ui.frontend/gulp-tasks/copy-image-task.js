'use strict';
const gulp = require('gulp');
const config = require('../clientlib.config');
const rename = require('gulp-rename');

const imageSrcPath = config.sourcePath.image;
const imageDestPath = config.destinationPath.image;

module.exports = () => {
  if (process.env.NODE_ENV === 'production') {
    gulp.src(config.dependenciesPath.image.src, {
      allowEmpty: true
    })
      .pipe(gulp.dest(config.dependenciesPath.image.dest));
    return true;
  } else {
    gulp.src(imageSrcPath, {
      allowEmpty: true
    })
      .pipe(rename({ dirname: '' }))
      .pipe(gulp.dest(imageDestPath));
    return true;
  }
};