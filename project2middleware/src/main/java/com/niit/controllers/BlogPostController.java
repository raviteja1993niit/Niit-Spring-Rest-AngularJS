package com.niit.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.BlogPostDao;
import com.niit.dao.NotificationDao;
import com.niit.dao.UserDao;
import com.niit.models.BlogPost;
import com.niit.models.ErrorClazz;
import com.niit.models.Notification;
import com.niit.models.User;

@Controller
public class BlogPostController {
	@Autowired
private BlogPostDao blogPostDao;
@Autowired
private UserDao userDao;
@Autowired
private NotificationDao  notificationDao;
	// input  : {'blogTitle':'..','blogContent':'.....'}
	@RequestMapping(value="/addblogpost",method=RequestMethod.POST)
	public ResponseEntity<?> addBlogPost(@RequestBody BlogPost blogPost,HttpSession session){
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
    		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		blogPost.setPostedOn(new Date());
		User postedBy=userDao.getUser(email);
		blogPost.setPostedBy(postedBy);
		try{
		blogPostDao.addBlogPost(blogPost);
		}catch(Exception e){
			ErrorClazz errorClazz=new ErrorClazz(8,"Unable to save blog post details.." + e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	@RequestMapping(value="/blogswaitingforapproval",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogsWaitingForApproval(HttpSession session){
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
    		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		//ROLE - AUTHORIZATION
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN")){
			ErrorClazz errorClazz=new ErrorClazz(9,"You are not authorized to view the content..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		List<BlogPost> blogsWaitingForApproval=blogPostDao.getBlogsWaitingForApproval();
		return new ResponseEntity<List<BlogPost>>(blogsWaitingForApproval,HttpStatus.OK);
	}
	@RequestMapping(value="/blogsapproved",method=RequestMethod.GET)
	public ResponseEntity<?> getBlogsApproved(HttpSession session){
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
    		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		
		List<BlogPost> blogsApproved=blogPostDao.getBlogsApproved();
		return new ResponseEntity<List<BlogPost>>(blogsApproved,HttpStatus.OK);
	}
	@RequestMapping(value="/getblog/{blogId}",method=RequestMethod.GET)
	public ResponseEntity<?> getBlog(@PathVariable int blogId,HttpSession session){
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
    		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		BlogPost blogPost=blogPostDao.getBlog(blogId);
		return new ResponseEntity<BlogPost>(blogPost,HttpStatus.OK);
	}
	@RequestMapping(value="/approveblogpost/{blogPostId}",method=RequestMethod.PUT)
	public ResponseEntity<?> approveBlogPost(@PathVariable int blogPostId,HttpSession session)
	{
	String email=(String)session.getAttribute("email");
	//NOT LOGGED IN
	if(email==null){
		ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
	}
	//ROLE - AUTHORIZATION
	User user=userDao.getUser(email);
	if(!user.getRole().equals("ADMIN")){
		ErrorClazz errorClazz=new ErrorClazz(9,"You are not authorized to view the content..");
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
	}
	blogPostDao.approveBlogPost(blogPostId);
	BlogPost blogPost=blogPostDao.getBlog(blogPostId);
	Notification notification=new Notification();
	notification.setBlogTitle(blogPost.getBlogTitle());
	notification.setStatus("Approved");//value of status is "Approved"  or "Rejected"
	notification.setUserToBeNotified(blogPost.getPostedBy().getEmail());
	notificationDao.addNotification(notification);
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	@RequestMapping(value="/rejectblogpost/{blogPostId}/{rejectionReason}")
	public ResponseEntity<?> rejectBlogPost(@PathVariable int blogPostId,@PathVariable String rejectionReason,HttpSession session){
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
    		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		//ROLE - AUTHORIZATION
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN")){
			ErrorClazz errorClazz=new ErrorClazz(9,"You are not authorized to view the content..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}
		BlogPost blogPost=blogPostDao.getBlog(blogPostId);
		
		Notification notification=new Notification();
		notification.setBlogTitle(blogPost.getBlogTitle());
		notification.setStatus("Rejected");
		notification.setUserToBeNotified(blogPost.getPostedBy().getEmail());
		notification.setRejectionReason(rejectionReason);
		notificationDao.addNotification(notification);
		blogPostDao.rejectBlogPost(blogPostId);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@RequestMapping(value="/update" , method=RequestMethod.PUT)
	public ResponseEntity<?> updateBlogPost(@RequestBody BlogPost blogPost,HttpSession session)
	{
		String email=(String)session.getAttribute("email");
		//NOT LOGGED IN
		if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		//ROLE - AUTHORIZATION
		/*User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN")){
			ErrorClazz errorClazz=new ErrorClazz(9,"You are not authorized to view the content..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}*/
		try{
			System.out.println("BlogPost Updated");
			blogPost.setApproved(false);
			blogPost.setPostedOn(new Date());
			blogPostDao.updateBlogPost(blogPost);
		}
		catch(Exception e)
		{
			ErrorClazz errorClazz=new ErrorClazz(10,"UNABLE TO UPDATE BLOGPOST CONTENT"+e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	@RequestMapping(value="/getblogbyemail",method=RequestMethod.GET)  
	public ResponseEntity<?> getBlog(HttpSession session){                                 
		String email=(String)session.getAttribute("email");
		/*//NOT LOGGED IN
		if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
    		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		//ROLE - AUTHORIZATION
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN")){
			ErrorClazz errorClazz=new ErrorClazz(9,"You are not authorized to view the content..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}*/
		List<BlogPost> blogPosts=blogPostDao.getBlogByEmail(email);
		return new ResponseEntity<List<BlogPost>>(blogPosts,HttpStatus.OK);
	}
	@RequestMapping(value="/getblogbyemail1",method=RequestMethod.GET)  
	public ResponseEntity<?> getBlog1(HttpSession session){                                 
		String email=(String)session.getAttribute("email");
		/*//NOT LOGGED IN
		if(email==null){
			ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
    		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
		}
		//ROLE - AUTHORIZATION
		User user=userDao.getUser(email);
		if(!user.getRole().equals("ADMIN")){
			ErrorClazz errorClazz=new ErrorClazz(9,"You are not authorized to view the content..");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}*/
		List<BlogPost> blogPosts=blogPostDao.getBlogByEmail1(email);
		return new ResponseEntity<List<BlogPost>>(blogPosts,HttpStatus.OK);
	}
	
}


