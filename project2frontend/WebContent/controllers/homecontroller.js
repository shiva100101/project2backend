/**
 * Home Controller
 */
app.controller('HomeController',function($rootScope,$location,HomeService){
	
	function getNotification(){
		
		HomeService.getNotificationNotViewed().then(function(response){
			$rootScope.notificationNotViewed=response.data//select * from no.. where username=? and viewed=0
			$rootScope.notificationNotViewedLength=$rootScope.notificationNotViewed.length
			alert($rootScope.notificationNotViewedLength)
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
		HomeService.getNotificationViewed().then(function(response){
			$rootScope.notificationViewed=response.data//select * from no.. where username=? and viewed=1
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	if($rootScope.currentUser!=undefined){
	getNotification()
	}
	
	$rootScope.updateLength=function(){
		$rootScope.notificationNotViewedLength=0
		}
	$rootScope.updateNotification=function(notificationId){
		HomeService.updateNotification(notificationId).then(function(response){
			getNotification()
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
		}
})