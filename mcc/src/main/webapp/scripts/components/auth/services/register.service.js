'use strict';

angular.module('topsemApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


