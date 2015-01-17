'use strict';

angular.module('xmansApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
