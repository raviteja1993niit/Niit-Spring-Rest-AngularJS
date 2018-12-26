/**
 * BlogService
 */

app.factory('BlogService',function($http){
	var blogService={}
	var BASE_URL="http://localhost:8081/project2middleware"
		
	blogService.addBlog=function(blog){
		return $http.post(BASE_URL + "/addblogpost",blog)
	}
	
	blogService.blogsWaitingForApproval=function(){
		return $http.get(BASE_URL + "/blogswaitingforapproval")
	}
	
	blogService.blogsApproved=function(){
		return $http.get(BASE_URL + "/blogsapproved")
	}
	
	blogService.getBlog=function(blogId){
		return $http.get(BASE_URL + "/getblog/"+blogId)
	}
	blogService.approveBlogPost=function(blogPostId){
		return $http.put(BASE_URL + "/approveblogpost/"+blogPostId)
	}
	blogService.rejectBlogPost=function(blogPostId,rejectionReason){
    	if(rejectionReason==undefined)
    		rejectionReason='Not mentioned by Admin'
    	return $http['delete'](BASE_URL + "/rejectblogpost/"+blogPostId+"/"+rejectionReason)
    }	
	blogService.updateBlogPost=function(blogPost){
		return $http.put(BASE_URL + "/update",blogPost)
	}
	blogService.blogswaitingForApprovalPostedByUser=function(){
		return $http.get(BASE_URL + "/getblogbyemail")
	}
	blogService.blogswaitingForApprovalPostedByUser1=function(){
		return $http.get(BASE_URL + "/getblogbyemail1")
	}
	 blogService.hasUserLikedBlogPost=function(blogPostId){
	    	return $http.get(BASE_URL + "/hasuserlikedblogpost/"+blogPostId)
	    }
	    
	    blogService.updateLikes=function(blogPostId){
	    	return $http.put(BASE_URL + "/updatelikes/"+blogPostId)
	    }
	    blogService.addBlogComment=function(blogComment){
	    	return $http.post(BASE_URL + "/addblogcomment",blogComment)
	    }
	    blogService.updateBlogComment=function(blogComment){
	    	return $http.put(BASE_URL + "/updateblogcomment",blogComment)
	    }
	    
	    blogService.getBlogComments=function(blogPostId){
	    	return $http.get(BASE_URL + "/blogcomments/"+blogPostId)
	    }
	    blogService.getBlogCommentById=function(blogCommentId){
	    	 alert("As")
	    	return $http.get(BASE_URL + "/getblogcommentbyid/"+blogCommentId)
	    }
	return blogService
})