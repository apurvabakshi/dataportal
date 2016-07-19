app.controller('LoginController', ['$scope', function($scope) { 
  $scope.title = 'Login App'; 
$scope.tab=1;
  
  $scope.login = function() {
	//  alert("login method called");
	    if ($scope.username === 'admin' && $scope.password === 'pass') {
	    var name=$scope.username;
	    	  window.location="Homepage.html?username=" + name;
	      authentication.isAuthenticated = true;
	      authentication.user = { name: $scope.username };
	      alert("User authenticated");
	      $scope.title = 'authenticated'; 
	   
	    } else {
	      $scope.loginError = "Invalid username/password combination";
	      console.log('Login failed..');
	    };
  };
 
	 
	 $scope.currentTab = 1;

	 $scope.onClickTab = function (tab) {
	        $scope.tab =tab;
	    }
	 
	 $scope.isActiveTab = function(tabUrl) {
	        return tabUrl == $scope.currentTab;
	    }
}]);