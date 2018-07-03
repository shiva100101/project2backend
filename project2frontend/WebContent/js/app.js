/**
 *  Angular Js module 
 */
var app=angular.module("app",['ngRoute','ngCookies'])
app.config(function($routeProvider){
	$routeProvider
	.when('/register',{
		templateUrl:'views/registerationform.html',
		controller:'UserController'
	})
	.when('/login',{
		templateUrl:'views/login.html',
		controller:'UserController'
	})
	.when('/editprofile',{
		templateUrl:'views/userprofile.html',
		controller:'UserController'
	})
	.when('/addjob',{//Data is from jobform.html to Controller
		templateUrl:'views/jobform.html',
		controller:'JobController'
	})
	.when('/alljobs',{ // from controller to view [$scope.persons=[] ]
		templateUrl:'views/jobslist.html',
		controller:'JobController'
	})
	.when('/addblog',{ 
		templateUrl:'views/blogform.html', // view to controller
		controller:'BlogPostController'
	})
	.when('/getblogs',{ 
		templateUrl:'views/blogslist.html',// controller to view
		controller:'BlogPostController'
	})
	.when('/admin/getblog/:id',{ 
		templateUrl:'views/approvalform.html',// controller to view
		controller:'BlogPostDetailsController'
	})
	.when('/getblog/:id',{ 
		templateUrl:'views/blogdetails.html',// controller to view
		controller:'BlogPostDetailsController'
	})
	.when('/updateprofile',{
		templateUrl:'views/uploadprofilepic.html'
	})
	.when('/suggestedusers', {
        templateUrl : 'views/suggestedusers.html',
        controller:'FriendController'
    })
    .when('/pendingrequests',{
		templateUrl:'views/pendingRequests.html',
		controller:'FriendController'
	})
	.when('/getfriends',{
		templateUrl:'views/listoffriends.html',
		controller:'FriendController'
	})
	.when('/chat',{
		templateUrl:'views/chat.html',
		controller:'ChatCtrl'
	})
	.when('/home',{ 
		templateUrl:'views/home.html',// controller to view
		controller:'HomeController'
	})
	.when('/uploadpic',{ 
		templateUrl:'views/profilepicture.html',// controller to view
	})
	.otherwise({templateUrl:'views/home.html',controller:'HomeController'})
})
app.run(function($rootScope,$cookieStore,UserService,$location){
	alert($cookieStore.get('currenrUser'))
	if($rootScope.currentUser==undefined)
		$rootScope.currentUser=$cookieStore.get('currentUser')
		
		$rootScope.logout=function(){
		/*
		 * Call middleware logout url -> Middleware - remove HttpSession attribute,update online status to false
		 * on success - in frontend, remove cookieStore attribute currentUser, delete $rootScope
		 */
		UserService.logout().then(function(response){
			delete $rootScope.currentUser;
			$cookieStore.remove('currentUser')
			$location.path('/login')
		},function(response){
			console.log(response.status)
			$location.path('/login')
		})
	}
})