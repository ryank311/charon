module.exports = function(grunt) {

  grunt.initConfig({
    compass: {                  // Task
      dist: {                   // Target
        options: {              // Target options
          sassDir: 'assets/sass',
          cssDir: 'resources/public/css',
          environment: 'production'
        }
      },
      dev: {                    // Another target
        options: {
          sassDir: 'assets/sass',
          cssDir: 'resources/public/css',
        }
      }
    },
    copy: {
      main: {
        files: [
          // includes files within path
          {src: ['bower_components/fontawesome/css/font-awesome.min.css'], dest: 'resources/public/css/font-awesome.min.css'},
          {src: ['bower_components/jquery/dist/jquery.min.js'], dest: 'resources/public/js/jquery.min.js'},
          {src: ['bower_components/webcomponentsjs/webcomponents.min.js'], dest: 'resources/public/js/webcomponents.min.js'},
          {src: ['bower_components/polymer/polymer.min.js'], dest: 'resources/public/js/polymer.min.js'}
        ],
      },
    },
  });

  grunt.loadNpmTasks('grunt-contrib-compass');
  grunt.loadNpmTasks('grunt-contrib-copy');

  // Default task(s).
  grunt.registerTask('default', ['copy', 'compass']);
  

};