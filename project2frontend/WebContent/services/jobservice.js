/**
 * 
 */
app.factory('JobService',function($http){
	var jobService={}
	var BASE_URL="http://localhost:8081/project2middleware/"
	
	jobService.addjob=function(job){
		console.log($http.job)
		return $http.post(BASE_URL +"/savejob",job)
	}
	jobService.getAllJobs=function(){
		return $http.get(BASE_URL+"/alljobs")
	}
	jobService.getJob=function(jobId){
		return $http.get("http://localhost:8081/project2middleware/getjob/"+jobId)
	}
	
	return jobService;
})