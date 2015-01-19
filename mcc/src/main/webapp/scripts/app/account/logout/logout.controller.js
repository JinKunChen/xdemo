'use strict';

angular.module('topsemApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
