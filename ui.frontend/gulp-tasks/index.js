'use strict';
const sassTask = require('./sass-task');
const jsTask = require('./js-task');
const assembleTask = require('./assemble-task');
const copyImgTask = require('./copy-image-task');
const copyFontTask = require('./copy-fonts-task');
const copyApiTask = require('./copy-api-task');
const cleanTask = require('./clean-task');
const cleanLocalTask = require('./clean-local-task');

module.exports = {
    sassTask,
    jsTask,
    assembleTask,
    copyFontTask,
    copyApiTask,
    copyImgTask,
    cleanTask,
    cleanLocalTask
};