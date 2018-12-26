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
import com.niit.dao.BlogCommentDao;
import com.niit.dao.UserDao;
import com.niit.models.BlogComment;
import com.niit.models.BlogPost;
import com.niit.models.ErrorClazz;
import com.niit.models.User;

@Controller
public class BlogCommentController {
	@Autowired
	private BlogCommentDao blogCommentDao;
		@Autowired
	private UserDao userDao;
		
		@RequestMapping(value="/addblogcomment",method=RequestMethod.POST)
		//create a blogcomment and set the values for two  properties "commentTxt" and "blogPost" in frontend
		public ResponseEntity<?> addBlogComment(@RequestBody BlogComment blogComment,HttpSession session){
			String email=(String)session.getAttribute("email");
			//NOT LOGGED IN
			if(email==null){
				ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
	    		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
			}
			blogComment.setCommentedOn(new Date());
			User commentedBy=userDao.getUser(email);
			blogComment.setCommentedBy(commentedBy);
			blogCommentDao.addBlogComment(blogComment);
			return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
		}
	   @RequestMapping(value="/blogcomments/{blogPostId}",method=RequestMethod.GET)
	   public ResponseEntity<?> getAllBlogComments(@PathVariable int blogPostId,HttpSession session){
		   String email=(String)session.getAttribute("email");
			//NOT LOGGED IN
			if(email==null){
				ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
	   		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
			}
			List<BlogComment> blogComments=blogCommentDao.getAllBlogComments(blogPostId);
			
			return new ResponseEntity<List<BlogComment>>(blogComments,HttpStatus.OK);
	   }
	   @RequestMapping(value="/updateblogcomment",method=RequestMethod.PUT)
		//create a blogcomment and set the values for two  properties "commentTxt" and "blogPost" in frontend
		public ResponseEntity<?> updateBlogComment(@RequestBody BlogComment blogComment,HttpSession session){
			String email=(String)session.getAttribute("email");
			//NOT LOGGED IN
			if(email==null){
				ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
	    		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
			}
			blogComment.setCommentedOn(new Date());
			User commentedBy=userDao.getUser(email);
			blogComment.setCommentedBy(commentedBy);
	        int cid=blogComment.getCommentId();
			try
			{
				System.out.println("MiddleWare"+cid);
				blogCommentDao.updateBlogComment(blogComment);
			}
			catch(Exception e)
			{
				ErrorClazz errorClazz=new ErrorClazz(10,"Unable To Update Blog Comment"+e.getMessage());
				return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			BlogComment blogComment1 = blogCommentDao.getBlogCommentById(cid);
			return new ResponseEntity<BlogComment>(blogComment1,HttpStatus.OK);
		}
	   @RequestMapping(value="/deleteblogcomment",method=RequestMethod.DELETE)
		//create a blogcomment and set the values for two  properties "commentTxt" and "blogPost" in frontend
		public ResponseEntity<?> deleteBlogComment(@PathVariable int commentId,HttpSession session){
			String email=(String)session.getAttribute("email");
			//NOT LOGGED IN
			if(email==null){
				ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
	    		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
			}
			blogCommentDao.deleteBlogComment(commentId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
	   @RequestMapping(value="/getblogcommentbyid/{blogCommentId}",method=RequestMethod.GET)
		//create a blogcomment and set the values for two  properties "commentTxt" and "blogPost" in frontend
		public ResponseEntity<?> getBlogCommentById(@PathVariable int blogCommentId,HttpSession session){
			String email=(String)session.getAttribute("email");
			//NOT LOGGED IN
			if(email==null){
				ErrorClazz errorClazz=new ErrorClazz(6,"Please login...");
	    		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);//login.html
			}
			BlogComment blogComment = blogCommentDao.getBlogCommentById(blogCommentId);
			return new ResponseEntity<BlogComment>(blogComment,HttpStatus.OK);
		}
	   
}
