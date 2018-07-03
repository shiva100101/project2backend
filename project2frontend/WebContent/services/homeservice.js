/**
 * HomeService 
 */
app.factory('HomeService',function($http){
	var homeService={}
	var BASE_URL="http://localhost:8081/project2middleware"
	homeService.getNotificationNotViewed=function(){
		//select * from notification where username=? and viewed=0
		return $http.get(BASE_URL+"/getnotification/"+0)
	}
	
	homeService.getNotificationViewed=function(){
		//select * from notification where username=? and viewed=1
		return $http.get(BASE_URL+"/getnotification/"+1)
	}
	
	homeService.updateNotification=function(notificationId){
		
		return $http.put(BASE_URL+"/updatenotification/"+notificationId)
	}
	
	return homeService;
})