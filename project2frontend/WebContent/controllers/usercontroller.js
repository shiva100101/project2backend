/**
 * UserController
 */
app.controller('UserController',function($scope,UserService,$location,$rootScope,$cookieStore){
	$scope.msg="Registered successfully... please login"
	//Only for edit, this statement will get executed
	//it will not get executed for registration
	if($rootScope.currentUser!=undefined){//Fetch user details
		UserService.getUser().then(function(response){
			$scope.user=response.data //User object	
		},function(response){//401
			delete $rootScope.currentUser;
			$cookieStore.remove('currentUser')
			if(response.status==401){
				$location.path('/login')
			}
		})
		
	}
	
	$scope.registerUser=function(){  //2
		console.log($scope.user)
		UserService.registerUser($scope.user) //3
		.then(function(response){
			$scope.msg="Registered successfully... please login"
			$location.path('/login')
		},function(response){
			console.log(response.data)
			console.log(response.status)
			$scope.error=response.data   // Error clazz object in JSON
		}) //9 	 	
	}
	$scope.login=function(){
		UserService.login($scope.user).then(function(response){
			$rootScope.currentUser=response.data
			$cookieStore.put('currentUser',response.data)
			$location.path('/home')
		},function(response){//401,500...
			if(response.status==401){
				$scope.error=response.data
				$location.path('/login')
			}
		})
	}
	$scope.editUserProfile=function(){
		UserService.editUserProfile($scope.user).then(function(response){
			alert("Updated Successfully")
			$location.path('/home')
		},function(response){//401,500
			if(response.status==401)
				$location.path('/login')
			if(response.status==500){
				$scope.error=response.data
				$location.path('/editprofile')
			}
		})
	}
})






















