'use strict';
const gulp = require('gulp');
const config = require('../clientlib.config');

const sourcePath = config.sourcePath;
const destinationPath = config.destinationPath;
const srcAPIs = sourcePath.api;
const distAPIs = destinationPath.api;

module.exports = () => {
  gulp.src(srcAPIs, {
      allowEmpty: true
    })
    .pipe(gulp.dest(distAPIs));
};