var app = angular.module('aswhcm-tracker', ["ngRoute"]);

app.config(function ($routeProvider) {
  $routeProvider
    .when('/', {
      templateUrl: "modules/authentication/login.html"
    })
});
