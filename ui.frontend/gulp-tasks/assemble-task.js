'use strict';
const assemble = require('assemble');
const extname = require('gulp-extname');
const browserSync = require('browser-sync');
const config = require('../clientlib.config');

const sourcePath = config.sourcePath;
const destinationPath = config.destinationPath;
const srcHTMLPath = sourcePath.html;
const srcLayouts = srcHTMLPath.layouts;
const srcPartials = srcHTMLPath.partials;
const srcData = srcHTMLPath.data;
const srcPages = srcHTMLPath.pages;
const distHTML = destinationPath.html;

module.exports = () => {
  assemble.layouts(srcLayouts);

  // look for partials from different locations
  if (srcPartials) {
    for (var i = 0; i < srcPartials.length; i++) {
      assemble.partials(srcPartials[i]);
    }
  }
  assemble.data(srcData);
  assemble.src(srcPages)
    .pipe(extname())
    .pipe(assemble.dest(distHTML))
    .pipe(browserSync.stream());
};