angular.module('Login')
.controller('LoginController', function($scope){
	$scope.username = "test";
	$scope.password = "test";

	$scope.count = 0;
	
	$scope.SendData = function(){
		var data = $.param({
			userId : $scope.username,
			passw : $scope.password
		});
		
		var config = {
				headers : {
					'Content-Type':'application/json',
					'Accept':'application/json'
				}
		}
		

		$scope.count = $scope.count + 1;;
	};
	
});