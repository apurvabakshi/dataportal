/**
 * 
 */


var app = angular.module('homePage', []);


app.controller('tabCtrl', function($scope) {


  $scope.text = "Hello Angular!";

	 $scope.tabs = [{
         title: 'One'
       
     }, {
         title: 'Two'
     }, {
         title: 'Three'
         
 }];
	 
	 $scope.currentTab = 'One';

	 //Function 1	 
	 $scope.onClickTab = function (tab) {
	        $scope.currentTab = tab.title;
	    }
	 
	 //Function 2
	 $scope.isActiveTab = function(tabTitle) {
	        return tabTitle == $scope.currentTab;
	    }
});