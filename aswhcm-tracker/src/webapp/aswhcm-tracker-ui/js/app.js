
// declare modules
angular.module('Login', []);
angular.module('Home', []);

angular.module('aswhcm-tracker', ['ngRoute','Login'])


.config(function ($routeProvider) {
  $routeProvider
    .when('/', {
      controller: 'LoginController',
      templateUrl: "aswhcm-tracker-ui/modules/authentication/login.html"
    })

    .otherwise({ redirectTo: '/' });
});
