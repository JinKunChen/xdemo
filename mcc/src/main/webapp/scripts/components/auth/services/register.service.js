'use strict';

angular.module('xmansApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


