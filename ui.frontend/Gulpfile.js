const gulp = require('gulp');
const gulpTask = require('./gulp-tasks');
const browserSync = require('browser-sync').create();
const config = require('./clientlib.config');
const reload = browserSync.reload;

const base = config.base;

// Handlebar compilation using assemble
function markupCompile(done) {
  gulpTask.assembleTask();
  done();
}

// SCSS files compile and minification task
function sass(done) {
  gulpTask.sassTask();
  done();
}

// JS concat and minification task
function js(done) {
  gulpTask.jsTask();
  done();
}

// Copy image to the dist folder
function copyImg(done) {
  gulpTask.copyImgTask();
  done();
}


// Copy fonts to the dist folder
function copyFont(done) {
  gulpTask.copyFontTask();
  done();
}

// Copy api to the dist folder

function copyApi(done) {
  gulpTask.copyApiTask();
  done();
}

// Watch function
function watch(done) {
  gulp.watch([base.src + '**/*.js', base.src + '**/*.es6'], gulp.parallel(js))
    .on('change', reload);
  gulp.watch(base.src + '**/*.scss', gulp.parallel(sass))
    .on('change', reload);
  gulp.watch(base.src + '**/*.hbs', gulp.parallel(markupCompile))
    .on('change', reload);
  done();
}

function clean(done) {
  gulpTask.cleanTask(done);
}

function cleanLocal(done) {
  gulpTask.cleanLocalTask(done);
}

// Run Frontend server
function browserSyncInit(done) {
  browserSync.init({
    port: 3005,
    open: true,
    server: {
      baseDir: base.dist,
      https: false,
      middleware: [{
        route: '/',
        handle: (req, res, next) => {
          res.writeHead(302, {
            'Location': '/pages/'
          });
          res.end();
          next();
        }
      }]
    },
  }, () => {
    console.log('APPLICATION IS WATCHING FOR CHANGES');
  });
  done();
}

exports.default = gulp.series(cleanLocal, gulp.parallel(js, sass, copyImg, copyFont, copyApi, markupCompile), watch, browserSyncInit);

// exports.prod = gulp.series(clean, js, sass, copyImg, copyFont);
exports.prod = gulp.series(clean, js, sass, copyImg, copyFont);