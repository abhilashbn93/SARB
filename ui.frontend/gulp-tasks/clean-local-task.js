'use strict';
const del = require('del');
const config = require('../clientlib.config');

const destinationPath = config.destinationPath;

module.exports = (done) => {
    del([destinationPath.html + '*.*', destinationPath.css + '*.*', destinationPath.js + '*.*', destinationPath.api + '*.*', destinationPath.font + '*.*', destinationPath.image + '*.*'], {
        force: true
    }).then(() => {
        done();
    });
};