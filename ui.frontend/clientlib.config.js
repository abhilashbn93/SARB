'use strict';
const PATH = require('path');

const root = './';
const src = root + 'src/';
const componentSrc = src + 'components/';
const libsSrc = src + 'libs/';
const libsJSSrc = libsSrc + 'js/';
const libsCSSSrc = libsSrc + 'scss/';
const dist = root + 'dist/';
const AEMRoot = __dirname;
const nodeModulesPath = root + 'node_modules/';
const clientlibsPath = PATH.dirname(AEMRoot) +
    '/platform/ui.apps/src/main/content/jcr_root/apps/sarb/platform/clientlibs/';
const dependenciesDir = clientlibsPath + 'clientlibs-dependencies/';
const baseDir = clientlibsPath + 'clientlibs-base/';

exports.base = {
    root,
    src,
    dist
};

exports.sourcePath = {
    html: {
        layouts: src + 'layouts/**/*.hbs',
        partials: [componentSrc + '**/*.hbs'],
        pages: src + 'pages/**/*.hbs',
        data: src + 'data/*.json'
    },
    css: [
        nodeModulesPath + 'bootstrap/scss/bootstrap.scss',
        libsCSSSrc + 'libs-dev.scss',
        libsCSSSrc + 'vendor.scss',
        componentSrc + 'components.scss'
    ],
    js: {
        vendorLibs: [
            nodeModulesPath + 'jquery/dist/jquery.js',
            nodeModulesPath + 'handlebars/dist/handlebars.js',
            nodeModulesPath + 'bootstrap/dist/js/bootstrap.js',
            libsJSSrc + 'custom/polyfills.js'
        ],
        components: componentSrc + '**/*.es6',
        autoBinderJs: componentSrc + 'autobinder.es6'
    },
    api: src + 'api/**/*',
    font: src + 'resources/fonts/**/*',
    image: src + 'resources/images/**/*'
};

exports.destinationPath = {
    html: dist + 'pages/',
    css: dist + 'css/',
    js: dist + 'js/',
    api: dist + 'api/',
    font: dist + 'fonts/',
    image: dist + 'resources/images/'
};

exports.dependenciesPath = {
    css: {
        src: [
            nodeModulesPath + 'bootstrap/scss/bootstrap.scss',
        ],
        dest: dependenciesDir + 'css/'
    },
    js: {
        src: [
            libsJSSrc + 'custom/polyfills.js',
            nodeModulesPath + 'handlebars/dist/handlebars.js'
        ],
        dest: dependenciesDir + 'js/'
    },
    image: {
        src: src + 'resources/images/*.*',
        dest: dependenciesDir + 'resources/images/'
    },
    font: dependenciesDir + 'resources/fonts/'
};

exports.basePath = {
    css: [{
        src: libsCSSSrc + '/libs.scss',
        dest: baseDir + 'global/v1/global/css/'
    }, {
        src: componentSrc + 'header/header.scss',
        dest: baseDir + 'header/v1/header/css/'
    }, {
        src: componentSrc + 'footer/footer.scss',
        dest: baseDir + 'footer/v1/footer/css/'
    }],
    js: [{
        src: componentSrc + 'header/header.es6',
        dest: baseDir + 'header/v1/header/js/'
    }, {
        src: componentSrc + 'footer/footer.es6',
        dest: baseDir + 'footer/v1/footer/js/'
    }]
};
