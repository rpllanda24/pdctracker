var app = angular.module('aswhcm-tracker', ["ngRoute"]);

app.config(function ($routeProvider) {
  $routeProvider
    .when('/', {
      templateUrl: "aswhcm-tracker-ui/modules/authentication/login.html"
    })
});
