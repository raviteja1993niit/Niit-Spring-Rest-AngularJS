/**
 * BlogInDetailCtrl
 */
app.controller('BlogInDetailCtrl',function($scope,BlogService,$routeParams,$location,$sce){
	var blogId=$routeParams.blogId
	var blogPostId=$routeParams.blogPostId
	var user_email=$routeParams.user_email
	var blogCommentId=$routeParams.blogCommentId
	$scope.isRejected=false
	if($routeParams.blogId!=undefined){
		BlogService.getBlog(blogId).then(function(response){
			//query? select * from blogpost where blogpostid=?
			$scope.blogPost=response.data  //blogapprovalform.html or blogdetails.html
			$scope.htmlContent=$sce.trustAsHtml($scope.blogPost.blogContent)
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
		BlogService.hasUserLikedBlogPost(blogId).then(
		function(response){
			//response.data is either 1 blogpostlikes object or empty value
			if(response.data=='')//the blogpost is not yet liked by the user
				$scope.isLiked=false
				else
					$scope.isLiked=true
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	$scope.approveBlogPost=function(blogPostId)
	{
		BlogService.approveBlogPost(blogPostId).then(function (response){
			$location.path('/blogswaitingforapproval/0')
		}),function(response){
			$scope.error=response.data
			if(response.status == 401)
				$location.path('/login')
		}
	}

	$scope.rejectBlogPost=function(blogPostId,rejectionReason){
		BlogService.rejectBlogPost(blogPostId,rejectionReason).then(function(response){
			$location.path('/blogswaitingforapproval/0')
		},function(response){
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
		})
	}
	
	//http://localhost:8080/middlewarecruddemo/getbook?isbn=undefined
	if(blogPostId!=undefined){
	BlogService.getBlog(blogPostId).then(function(response){
			$scope.blogPost=response.data
		},function(response){
			console.log(response)
		})
	}

	$scope.updateBlogPost=function(blogPost)
	{
		alert('BlogPost details Updated successfully...')
		BlogService.updateBlogPost(blogPost).then(function (response){
			$scope.blogPost={}
		}),function(response){
			$scope.error=response.data
			if(response.status == 401)
				$location.path('/login')
		}
	}
	
	/*$scope.blogswaitingForApprovalPostedByUser=function(email)
	{
		alert('Blogs Waiting For Approval Posted By User...')
		BlogService.blogswaitingForApprovalPostedByUser(email).then(function (response){
			$scope.blogs=response.data
		}),function(response){
			$scope.error=response.data
			if(response.status == 401)
				$location.path('/login')
		}
	}*/
	function blogswaitingForApprovalPostedByUser(){
		alert('Update Waiting Blogs...')
		BlogService.blogswaitingForApprovalPostedByUser().then(function (response){
			$scope.blogposts=response.data
		},function(response){
			$scope.error=response.data 
			if(response.status==401)
				$location.path('/login')
		})
	}
	if($routeParams.value==0)
		{
	blogswaitingForApprovalPostedByUser()//call the function only if logged in user role is 'ADMIN'
		}
	function blogswaitingForApprovalPostedByUser1(){
		alert('Update Approved Blogs...')
		BlogService.blogswaitingForApprovalPostedByUser1().then(function (response){
			$scope.blogposts1=response.data
		},function(response){
			$scope.error=response.data 
			if(response.status==401)
				$location.path('/login')
		})
	}
	if($routeParams.value==1)
		{
	blogswaitingForApprovalPostedByUser1()//call the function only if logged in user role is 'ADMIN'
		}
	$scope.showRejectionTxt=function()
	{
		$scope.isRejected=!$scope.isRejected
	}
	
	   $scope.updateLikes=function(blogPostId){
		   BlogService.updateLikes(blogPostId).then(
		    function(response){
		    	$scope.blogPost=response.data
		    	$scope.isLiked=!$scope.isLiked
		    },function(response){
		    	if(response.status==401)
					$location.path('/login')
		   })
	   }
	   $scope.addBlogComment=function(commentTxt,blogPost){
		   var blogComment={}
		   blogComment.commentTxt=commentTxt
		   blogComment.blogPost=blogPost
		   console.log(blogComment)
		   BlogService.addBlogComment(blogComment).then(function(response){
			   $scope.blogComment=response.data
			   $scope.commentTxt=""
		   },function(response){
			   if(response.status==401)
					$location.path('/login')
		   })
	   }
		
	   $scope.getBlogComments=function(blogPostId){
		   BlogService.getBlogComments(blogPostId).then(
		   function(response){
			   $scope.comments=response.data //it is List<BlogComment> 
		   },function(response){
			   if(response.status==401)
					$location.path('/login')
		   })
	   }
	   $scope.updateBlogComment=function(commentTxt,blogPost){
		   var blogComment={}
		   blogComment.commentTxt=commentTxt
		   blogComment.blogPost=blogPost
		   blogComment.commentId=blogCommentId
		   console.log(blogComment)
		   BlogService.updateBlogComment(blogComment).then(function(response){
			   $scope.blogComment=response.data
			   $scope.commentTxt=""
		   },function(response){
			   if(response.status==401)
					$location.path('/login')
		   })
	   }
	   if(blogCommentId!=undefined){
		   BlogService.getBlogCommentById(blogCommentId).then(function(response){
					$scope.comment1=response.data
					var comment=response.data
					$scope.blogPost=comment.blogPost
				},function(response){
					console.log(response)
				})
			}
})