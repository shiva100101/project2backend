/**
 * 
 */
app.controller('JobController',function($scope,JobService,$location){
	$scope.showDetails=false
	//$scope.searchTxt="Java Developer"
	$scope.addjob=function(){
		//console.log($http.user)
		console.log($scope.job)
		JobService.addjob($scope.job).then(
				function(response){
					alert('Job details posted successfully')
					$location.path('/home')
				},function(response){
					if(response.status==401){
						if(response.data.code==6){
							alert('Access Denied')
						}
						else{
						$scope.error=response.data
						$location.path('/login')
					}
					}
					if(response.status==500){
						$scope.error=response.data
						$location.path('/addjob')
					}
				})
	}
	$scope.getJob=function(jobId){
		$scope.showDetails=true
		JobService.getJob(jobId).then(function(response){
			$scope.job=response.data
		},function(response){
			if(response.status==401){
				$scope.error=response.data
				$location.path('/login')
			}
		})
	}
	
	function getAllJobs(){
		JobService.getAllJobs().then(function(response){
			$scope.jobs=response.data // List<Job> in json format
		},function(response){
			if(response.status==401){
				$scope.error=response.data
				$location.path('/login')
			}
		})
	}
	getAllJobs() // function call
})