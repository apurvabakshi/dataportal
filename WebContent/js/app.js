var app=angular.module("loginApp",[]);


/*login.config(function($routeProvider) {
	alert("in fun login");
  $routeProvider.when('/login.html', {
    templateUrl: 'login.html',
    controller: 'LoginCtrl'
  });
  $routeProvider.when('/', {
    templateUrl: 'home.html',
    controller: 'HomeCtrl'
  });
  $routeProvider.otherwise({ redirectTo: '/' });
});

login.run(function(authentication, $rootScope, $location) {
  $rootScope.$on('$routeChangeStart', function(evt) {
    if(!authentication.isAuthenticated){ 
      $location.url("/login");
    }
    event.preventDefault();
  });
})

login.controller('LoginCtrl', function($scope, $http, $location, authentication) {
  $scope.login = function() {
    if ($scope.username === 'admin' && $scope.password === 'pass') {
      console.log('successful')
      authentication.isAuthenticated = true;
      authentication.user = { name: $scope.username };
      alert("User authenticated");
      $location.url("/");
    } else {
      $scope.loginError = "Invalid username/password combination";
      console.log('Login failed..');
    };
  };
});

login.controller('AppCtrl', function($scope, authentication) {
  $scope.templates =
  [
  	{ url: 'login.html' },
  	{ url: 'home.html' }
  ];
    $scope.template = $scope.templates[0];
  $scope.login = function (username, password) {
    if ( username === 'admin' && password === '1234') {
  		authentication.isAuthenticated = true;
  		$scope.template = $scope.templates[1];
  		$scope.user = username;
    } else {
  		$scope.loginError = "Invalid username/password combination";
    };
  };
  
});

login.controller('HomeCtrl', function($scope, authentication) {
  $scope.user = authentication.user.name;
  
});

login.factory('authentication', function() {
  return {
    isAuthenticated: false,
    user: null
  }
});*/