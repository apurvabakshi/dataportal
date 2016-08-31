var app = angular.module('homeapp', []);

app.config([ '$httpProvider', function($httpProvider) {
	$httpProvider.defaults.cache = false;
} ]);

app.directive('fileModel', [ '$parse', function($parse) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
} ]);

app.service('fileUpload', [ '$http', function($http) {
	this.uploadFileToUrl = function(file, uploadUrl, p) {
		var fd = new FormData();
		fd.append('file', file);
		$http.post(uploadUrl, fd, {
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			},
			params : {
				projectname : p
			}
		}).success(function() {
			alert("Uploaded");
		}).error(function(request, error) {
			document.write("Error" + error);
		});

	};
} ]);

app.controller('homepageController', ['$scope','$http','fileUpload', function($scope,$http,fileUpload) { 
	
	
	  $scope.currentTab = 1;

	  $scope.onClickTab = function (tab) {
		        $scope.tab =tab;
		       
		        if(tab==1){
		        	$scope.getskillset();
		        }
		        if(tab==2){
		        	$scope.gettimelines();
		        }
		        if(tab==3){
		        	$scope.getupdates();
		        }
		        if(tab==4){
		        	$scope.getDocList();
		        }
		    }
		
	  
	  
		 $scope.getdetails = function(){
			 $scope.getskillset();
		 }
		 
		 //get username
		 var parameters = location.search.substring(1).split("=");
		 //get ID here and write a function to take username from id
		  var id = parameters[1];
		  
		  
		  //init
		  $scope.init=function()
			 {
			  $scope.projectList =  [];
			  try{
			    	  		  $http({method: "GET", 
			    			     url:"http://localhost:8080/dataportal/project/userprojects/getprojects/"+ id
			    		        }).
			    	        success(function(data, status, headers, config) {
			    	        	var jsontext = JSON.stringify(data);
			    	        	var jsonparsed=JSON.parse(jsontext);
			    	        	for( var i=0;i<jsonparsed.length;i++){
			    	        		 $scope.projectList.push({
				    	                    projectName: jsonparsed[i].name
				    	                });
			    	        	}
			    	        }).error(function(data, status, headers, config) {
			    	        });
				 }catch(e){document.write(e);
				 alert("Exception"+e);}
			 };//init
			 
			 

										  $scope.getskillset = function() {
								$scope.osList = [];
								$scope.ideList = [];
								$scope.languageList = [];
								$scope.dbList = [];
								$scope.skillsets=[];
								try {
									$http(
											{
												method : "GET",
												url : "http://localhost:8080/dataportal/project/userprojects/getskillsets/"
														+ $scope.projectname
											})
											.success(
													function(data, status,
															headers, config) {
														var jsontext = JSON
																.stringify(data);
														var jsonparsed = JSON
																.parse(jsontext);
														for (var j = 0; j < jsonparsed.length; j++) {
															switch (jsonparsed[j].name) {
															case "databases":
																var dbNames = jsonparsed[j].value
																		.split(",");
																i = 1;
																while (dbNames[i] != null) {
																	$scope.dbList
																			.push({
																				value : dbNames[i]
																			});
																	i++;
																}
																break;

															case "development_env":
																var ideNames = jsonparsed[j].value
																		.split(",");
																i = 1;
																while (ideNames[i] != null) {
																	$scope.ideList
																			.push({
																				value : ideNames[i]
																			});
																	i++;
																}
																break;

															case "languages":
																var langNames = jsonparsed[j].value
																		.split(",");
																i = 1;
																while (langNames[i] != null) {
																	$scope.languageList
																			.push({
																				value : langNames[i]
																			});
																	i++;
																}
																break;

															case "os":
																var osNames = jsonparsed[j].value
																		.split(",");
																i = 1;
																while (osNames[i] != null) {
																	$scope.osList
																			.push({
																				value : osNames[i]
																			});
																	i++;
																}
																break;
															}

														}
														$scope.skill=[$scope.dblist,$scope.oslist];
													}).error(
													function(data, status,
															headers, config) {
														alert("error");
													});
								} catch (e) {
									document.write(e);
									alert("Exception" + e);
								}
							};// getskillset

							
							  $scope.gettimelines=function()
								 {
								  $scope.timelineList =  [];
								  try{
								    	  		  $http({method: "GET", 
								    			     url:"http://localhost:8080/dataportal/project/userprojects/gettimelines/"+ $scope.projectname
								    		        }).
								    	        success(function(data, status, headers, config) {
								    	        	var jsontext = JSON.stringify(data);
								    	        	var jsonparsed=JSON.parse(jsontext);
								    	        	for( var i=0;i<jsonparsed.length;i++){
								    	        		 $scope.timelineList.push({
									    	                    date: jsonparsed[i].date,
									    	                    release: jsonparsed[i].release,
									    	                    feature:jsonparsed[i].features
									    	                });
								    	        	}
								    	        }).error(function(data, status, headers, config) {
								    	        });
									 }catch(e){document.write(e);
									 alert("Exception"+e);}
								 };//timelines
								 
								 
								 $scope.getupdates=function()
								 {
								  $scope.updatesList =  [];
								  try{
								    	  		  $http({method: "GET", 
								    			     url:"http://localhost:8080/dataportal/project/userprojects/getupdates/"+ $scope.projectname+"/"+$scope.projectmonth+"/"+$scope.projectyear
								    		        }).
								    	        success(function(data, status, headers, config) {
								    	        	var jsontext = JSON.stringify(data);
								    	        	var jsonparsed=JSON.parse(jsontext);
								    	        	for( var i=0;i<jsonparsed.length;i++){
								    	        		 $scope.updatesList.push({
									    	                    value: jsonparsed[i].update
									    	                });
								    	        	}
								    	        }).error(function(data, status, headers, config) {
								    	        });
									 }catch(e){document.write(e);
									 alert("Exception"+e);}
								 };//updates
								 
								 
								 
							
							// func : getdoclist
							$scope.getDocList = function() {
								  $scope.docList =  [];
								  $scope.status="";
								  try{     
								    	  		  $http({method: "GET", 
								    			     url:"http://localhost:8080/dataportal/project/userprojects/getdocs/"+ $scope.projectname
								    			   
								    		        }).
								    	        success(function(data, status, headers, config) {
								    	        	var jsontext = JSON.stringify(data);
								    	        	var jsonparsed=JSON.parse(jsontext);
								    	        	for( var i=0;i<jsonparsed.length;i++){
								    	        		 $scope.docList.push({
									    	                    name: jsonparsed[i].name
									    	                });
								    	        	}
								    	        }).error(function(data, status, headers, config) {
								    	        });
									 }catch(e){document.write(e);
									 alert("Exception"+e);}
				 };//getdoclist
				 
				 //file functions
				 $scope.upload = function() {
						var file = $scope.myFile;
						var fileinfo = JSON.stringify(file);
						var filename1 =file.name;
						var i = 0;
						while ($scope.docList[i] != null) {
							if (filename1
									.localeCompare($scope.docList[i].name) == 0) {
								alert("File already exists");
								return;
							}
							i++;
						}
						console.log('file is ' + JSON.stringify(file));
						var path = document.location.pathname;
						var dir = path.substring(
								path.indexOf('/', 1) + 1, path
										.lastIndexOf('/'));
						var uploadUrl = "http://localhost:8080/dataportal/upload/UploadServlet";
						fileUpload.uploadFileToUrl(file, uploadUrl,
								$scope.projectname);
					$scope.addDocs(file);
					};
					

							$scope.addDocs = function(file) {
								$scope.status = "Uploaded Successfully";
								var projectName = $scope.projectname;
								var fileinfo = JSON.stringify(file);
								var tmp = JSON.parse(fileinfo);
								try {
									$http(
											{
												url : "http://localhost:8080/dataportal/project/addprojectdetails/adddocs/"
														+ $scope.projectmonth
														+ "/"
														+ $scope.projectyear
														+ "/"
														+ projectName,
												method : "POST",
												data : file.name,
											}).success(
											function(data, status, headers,
													config) {
												$scope.status = data;
												$scope.memberName = "";
												$scope.getDocList();

											}).error(
											function(data, status, headers,
													config) {
												$scope.data = data
														|| "Request failed";
												$scope.status = "Error";
												alert("Service Unavailable:"
														+ URL1);
												alert("call fail");
											});
								} catch (e) {
									document.write(e);
								}
							};//adddocs
							
							
							//getreport
							$scope.getreport = function() {
								var projectName;
								$scope.link = 0;
								projectName = $scope.projectname;
								alert("Please wait...");

								try {

									$http(
											{
												method : "GET",
												url : "http://localhost:8080/dataportal/reports/ReportServlet",
												params : {
													quarter : $scope.projectmonth,
													year : $scope.projectyear,
													projectName : projectName,
													format: $scope.reportformat
												},
												cache : false
											})
											.success(
													function(data, status,
															headers, config) {
														$scope.status = "Report created";

														a = document
																.getElementById("report1");
														a
																.setAttribute(
																		"href",
																		"http://localhost:8080/dataportal/Reports/"
																				+ projectName
																				+ "/"
																				+ $scope.projectmonth
																				+ "_"
																				+ $scope.projectyear
																				+ "."+$scope.reportformat);
														$scope.link = 1;
													})
											.error(
													function(data, status,
															headers, config) {
														$scope.data = data
																|| "Request failed";
														$scope.status = "Error";
														alert("Service Unavailable:"
																+ URL1);
													});

								} catch (e) {
									document.write(e);
								}

							};//getreport
							
							
							///EDIT FUNCTIONS
							$scope.editProjectUpdate = function(value) {
							alert("in edit"+value);
							var projectName = subproject;
							val.oldvalue = val.oldvalue.trim();
							try {
								$http(
										{
											url : "http://10.35.34.179:8080/dataportal/project/editprojectdetails/editupdates/"

													+ $scope.projectmonth
													+ "/"
													+ $scope.projectyear
													+ "/"
													+ $scope.projectname,
											method : "POST",
											data : val.oldvalue + ","
													+ val.value,
										})
										.success(
												function(data, status,
														headers, config) {
													if (data
															.localeCompare("Access Denied") == 0) {
														alert("Access Denied");

													} else {
														$scope.status = data;
														$scope.newDate = "";
														alert("Updated");
														$scope.getupdates();
													}
												})
										.error(
												function(data, status,
														headers, config) {
													$scope.data = data
															|| "Request failed";
													$scope.status = "Error";
													alert("Service Unavailable:"
															+ URL1);
													alert("call fail");
												});
							} catch (e) {
								document.write(e);
							}
						};

							
						}]);
