/**
 * Angular module "app"
 */
var app=angular.module("app",['ngRoute','ngCookies'])
app.config(function($routeProvider){
	$routeProvider
	.when('/registerUser',{controller:'UserCtrl',templateUrl:'views/registrationform.html'})
	.when('/login',{controller:'UserCtrl',templateUrl:'views/login.html'})
	.when('/protectedresource',{controller:'UserCtrl',templateUrl:'views/protectedview.html'})
	.when('/getuser',{controller:'UserCtrl',templateUrl:'views/updateform.html'})
	.when('/addjob',{controller:'JobCtrl',templateUrl:'views/jobform.html'})
	.when('/getjob/:id',{controller:'JobCtrl',templateUrl:'views/updatejobform.html'})
	.when('/alljobs',{controller:'JobCtrl',templateUrl:'views/jobslist.html'})
	.when('/addblog',{controller:'BlogCtrl',templateUrl:'views/blogform.html'})
	.when('/blogswaitingforapproval/:value',{controller:'BlogCtrl',templateUrl:'views/blogswaitingforapproval.html'})
	.when('/blogswaitingforapprovalpostedbyuser/:value',{controller:'BlogInDetailCtrl',templateUrl:'views/blogswaitingforapprovalpostedbyuser.html'})
	.when('/blogswaitingforapprovalpostedbyuser1/:value',{controller:'BlogInDetailCtrl',templateUrl:'views/blogswaitingforapprovalpostedbyuser1.html'})
	.when('/getblogwaitingforapproval/:blogId',{controller:'BlogInDetailCtrl',templateUrl:'views/blogapprovalform.html'})
	.when('/getblogwaitingforapprovalforuser/:blogId',{controller:'BlogInDetailCtrl',templateUrl:'views/blogupdateformbyuser.html'})
	.when('/getblogapproved/:blogId',{controller:'BlogInDetailCtrl',templateUrl:'views/blogdetails.html'})
	.when('/blogsapproved/:value',{controller:'BlogCtrl',templateUrl:'views/blogsapproved.html'})
	.when('/updateblogpost/:blogPostId',{controller:'BlogInDetailCtrl',templateUrl:'views/updateblogform.html'})
	.when('/updateblogcomment/:blogCommentId',{controller:'BlogInDetailCtrl',templateUrl:'views/updatecommentsform.html'})
	.when('/home',{controller:'NotificationCtrl',templateUrl:'views/home.html'})
	.when('/getnotification/:notificationId',{controller:'NotificationCtrl',templateUrl:'views/notificationdetails.html'})
	.otherwise({controller:'UserCtrl',templateUrl:'views/home.html'})
})
//ngRoute -> $routeProvider and ng-view
//ngCookies -> $cookieStore

app.run(function($rootScope,$cookieStore,UserService,$location){
	if($rootScope.user==undefined)
		$rootScope.user=$cookieStore.get('user')
		
    $rootScope.logout=function(){
		UserService.logout().then(function(response){
			delete $rootScope.user
			$cookieStore.remove('user')
			$location.path('/login')
		},function(response){
			if(response.status==401){//session attribute email is not there in HttpSession
			delete $rootScope.user
			$cookieStore.remove('user')
			$location.path('/login')
			}
		})
	}
})
