angular.module('Login')
.controller('LoginController', function($scope, $http){

	$scope.count = 0;
	
	$scope.SendData = function(){
		
		if($scope.username && $scope.password)
			{
		
		var request = {
			userId : $scope.username,
			passw : $scope.password
		};
		
		var config = {
				headers : {
					'Content-Type':'application/json',
					'Accept':'application/json'
				}
		}
		
		$http.post('/aswhcm-tracker/service/authenticate', request, config)
        .success(function (data, status, headers, config) {
        	var success = data.success;
        	if (success == true)
        		{
        		$scope.response = data.results + " has succesfully logged in";
        		}
        	else{
        		$scope.response = data.results.cause;
        		}
        })
        .error(function (data, status, header, config) {
            $scope.response = "Data: " + data +
                "<hr />status: " + status +
                "<hr />headers: " + header +
                "<hr />config: " + config;
        });
			}

	};
	
});