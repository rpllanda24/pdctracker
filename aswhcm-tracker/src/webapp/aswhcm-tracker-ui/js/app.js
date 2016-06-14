// declare modules
angular.module('Login', []);
angular.module('Home', []);

angular.module('aswhcm-tracker', [ 'ngRoute', 'Login' ])

.config(function($routeProvider) {
	$routeProvider
	.when('/login', {
		controller : 'LoginController',
		templateUrl : "aswhcm-tracker-ui/modules/authentication/login.html"
	})
	.when('/home', {
		templateUrl : 'aswhcm-tracker-ui/modules/home/home.html'
	}).otherwise({
		redirectTo : '/login'
	});
	

	
	
});
