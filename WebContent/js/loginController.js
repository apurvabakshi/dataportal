app.controller('LoginController', ['$scope','$http', function($scope,$http) { 
  $scope.title = 'Login App'; 
  $scope.tab=1;
  $scope.serverip='localhost';
  
  $scope.login = function() {
	  try{
	    	  		  $http({method: "GET", 
	    			     url:"http://"+$scope.serverip+":8080/dataportal/portal/user/authenticate/"+$scope.username+"/"+$scope.password,
	    	  		  }).
	    	        success(function(data, status, headers, config) {
	    	        	var jsontext = JSON.stringify(data);
	    	        	var user = JSON.parse(jsontext);
	    	        	if(user.sessionId!=null){
	    	        		window.location="homepage1.html?userid=" + user.id;
	    	        	}
	    	        	else{
	    	        		alert("Incorrect username or password!!");
	    	        	}
	    	        		}
	    	        ).error(function(data, status, headers, config) {
	    	        	alert("Service Unavailable:"+URL1);
	    	        });
		 }catch(e){document.write(e); alert("Exception");}
	 
  };
 
	 
	 $scope.currentTab = 1;

	 $scope.onClickTab = function (tab) {
	        $scope.tab =tab;
	    }
	 
	 $scope.isActiveTab = function(tabUrl) {
	        return tabUrl == $scope.currentTab;
	    }
}]);