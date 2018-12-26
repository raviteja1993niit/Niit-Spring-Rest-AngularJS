/**
 * JobService
 */
app.factory('JobService',function($http){
	var jobService={}
	var BASE_URL="http://localhost:8081/project2middleware"
	
	jobService.addJob=function(job){
		return $http.post(BASE_URL + "/addjob",job)
	}
	
	jobService.updateJob=function(job){
		return $http.put(BASE_URL + "/updatejob",job)
	}
	
	jobService.deleteJob=function(id){
		return $http['delete'](BASE_URL + "/deletejob/"+id)
	}
	
	jobService.getAllJobs=function(){
		return $http.get(BASE_URL + "/alljobs")
	}
	jobService.getJob=function(id){
		return $http.get(BASE_URL +"/getjob?id="+id);
	}
	return jobService;
})
