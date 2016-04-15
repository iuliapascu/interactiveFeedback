var gulp = require('gulp');
var del = require('del');

var debug = require('gulp-debug');
var tsc = require('gulp-typescript');
var sourcemaps = require('gulp-sourcemaps');
var imagemin = require('gulp-imagemin');

var merge = require('merge-stream');
var minimist = require('minimist');

var knownOptions = {
    boolean: 'production',
    default: { production: false }
};

var options = minimist(process.argv.slice(2), knownOptions);
console.log(minimist);

var config = {
    'source': './src/',
    'sourceApp': './src/app/',
    'nodeModules': [
        './node_modules/angular2*/bundles/{angular2.dev,angular2-polyfills,http.dev,router.dev}.js',
        './node_modules/systemjs*/dist/system.src.js',
        './node_modules/rxjs*/bundles/Rx.js',
        './src/vendor/ng2-translate*/*',
        './src/types/**/*.d.ts',
        './src/app/libs/Chart.js*/Chart.min.js'
    ],
    'allTypeScript': './src/**/*.ts',
    'resources': ['./**/*.png', './**/*.jpg', './**/*.json', './**/*.woff', './**/*.ttf'],
    'allHtml': ['./src/index.html', './**/*.html'],
    'allStyles': ['./src/ifeed.css', './**/*.css'],
    'allJS': './**/*.js',
    'allTranslations': ['./i18n*/*.json'],

    'dist': './dist/static',
    'tsOutputPath': './dist/static/app',
    'allJavaScript': ['./dist/static/js/**/*.js'],
    'nodeModulesOutputPath': './dist/static/vendor/',

    'typings': './src/tools/typings/',
    'libraryTypeScriptDefinitions': './src/tools/typings/**/*.d.ts'
};

gulp.task('clean', /*['clean-ts'],*/ function () {
    return del(['dist', 'tmp']);
});


/**
 * Remove all generated JavaScript files from TypeScript compilation.
 */
gulp.task('clean-ts', function (cb) {
    var typeScriptGenFiles = [
        config.tsOutputPath + '/**/*.js',    // path to all JS files auto gen'd by editor
        config.tsOutputPath + '/**/*.js.map', // path to all sourcemap files auto gen'd by editor
        '!' + config.tsOutputPath + '/lib'
    ];

    // delete the files
    del(typeScriptGenFiles, cb);
});


gulp.task('compile-ts', ['clean'], function () {
    var tsProject = tsc.createProject('src/tsconfig.json');

    var sourceTsFiles = [config.allTypeScript,      //path to typescript files
        config.libraryTypeScriptDefinitions];       //reference to library .d.ts files


    var tsResult = gulp.src(sourceTsFiles)
        .pipe(sourcemaps.init())
        .pipe(tsc(tsProject));

    if (options.production) {
        tsResult.on('error', function(e) {
            throw e;
        });
    }

    return merge(
        tsResult.dts
            .pipe(gulp.dest(config.dist)),
        tsResult.js
            .pipe(sourcemaps.write('.'))
            .pipe(gulp.dest(config.dist)));
});

// Imagemin images and ouput them in dist
gulp.task('imagemin', ['clean'], function () {
    gulp.src(config.resources, {cwd: config.sourceApp})
        .pipe(imagemin())
        .pipe(gulp.dest(config.tsOutputPath + '/images'));
});

// Copy all other files to dist directly
gulp.task('copy', ['clean'], function () {
    // Copy html
    gulp.src(config.allHtml, {cwd: config.source})
        .pipe(gulp.dest(config.dist));

    // Copy styles
    gulp.src(config.allStyles, {cwd: config.source})
        .pipe(gulp.dest(config.dist));

    // Copy resources
    gulp.src(config.resources, {cwd: config.source})
        .pipe(gulp.dest(config.dist));

    // Copy translations
    gulp.src(config.allTranslations, {cwd: config.source})
        .pipe(gulp.dest(config.dist));

    // Copy node_modules
    gulp.src(config.nodeModules)
        .pipe(gulp.dest(config.nodeModulesOutputPath));

    // Copy all js files
    gulp.src(config.allJS, {cwd: config.source})
        .pipe(gulp.dest(config.dist));
});

gulp.task('build', ['compile-ts', 'imagemin', 'copy']);

gulp.task('default', ['build']);