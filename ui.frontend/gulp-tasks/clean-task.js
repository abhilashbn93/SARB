'use strict';
const del = require('del');
const config = require('../clientlib.config');

const dependenciesPath = config.dependenciesPath;
const basePath = config.basePath;

const destDependenciesJS = dependenciesPath.js.dest;
const destDependenciesCSS = dependenciesPath.css.dest;

const destBaseJS = basePath.js.map(el => el.dest + '*.js');
const destBaseCSS = basePath.css.map(el => el.dest + '*.css');

const cleanBase = (done) => {
  del(destBaseJS, {
    force: true
  }).then(() => {
    del(destBaseCSS, {
      force: true
    }).then(() => {
      done();
    });
  });
}

const cleanDependencies = (done) => {
  del(destDependenciesJS, {
    force: true
  }).then(() => {
    del(destDependenciesCSS, {
      force: true
    }).then(() => {
      del([dependenciesPath.font + '*.*', dependenciesPath.image.dest + '*.*'], {
        force: true
      }).then(() => {
        cleanBase(done);
      });
    });
  });
}

module.exports = (done) => {
  cleanDependencies(done);
}