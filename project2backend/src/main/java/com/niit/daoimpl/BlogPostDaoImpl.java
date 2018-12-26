package com.niit.daoimpl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.dao.BlogPostDao;
import com.niit.models.BlogPost;
import com.niit.models.User;
@Repository
@Transactional
public class BlogPostDaoImpl implements BlogPostDao {
	@Autowired
private SessionFactory sessionFactory;
	public void addBlogPost(BlogPost blogPost) {
		Session session=sessionFactory.getCurrentSession();
		session.save(blogPost);
	}
	public List<BlogPost> getBlogsWaitingForApproval() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogPost where approved=false");
		List<BlogPost> blogPostsWaitingForApproval=query.list();
		return blogPostsWaitingForApproval;
	}
	public List<BlogPost> getBlogsApproved() {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogPost where approved=true");
		List<BlogPost> blogPostsApproved=query.list();
		return blogPostsApproved;
	}
	public BlogPost getBlog(int blogId) {
		Session session=sessionFactory.getCurrentSession();
		BlogPost blogPost=(BlogPost)session.get(BlogPost.class,blogId);
		return blogPost;
	}
	public void approveBlogPost(int blogPostId) {
		Session session=sessionFactory.getCurrentSession();
		BlogPost blogPost=(BlogPost)session.get(BlogPost.class,blogPostId);
		blogPost.setApproved(true);
		session.update(blogPost);
	}
	public void rejectBlogPost(int blogPostId) {
		Session session=sessionFactory.getCurrentSession();
		BlogPost blogPost=(BlogPost)session.get(BlogPost.class,blogPostId);
		session.delete(blogPost);
		
	}
	public void updateBlogPost(BlogPost blogPost) {
		Session session=sessionFactory.getCurrentSession();
		 session.update(blogPost);
	}
	
	public List<BlogPost> getBlogByEmail(String user_email) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogPost where approved=? and postedBy.email=?");
		query.setBoolean(0, false);
		query.setString(1, user_email);
		List<BlogPost> blogPosts=query.list();
		return blogPosts;
	}
	public List<BlogPost> getBlogByEmail1(String email) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogPost where approved=? and postedBy.email=?");
		query.setBoolean(0, true);
		query.setString(1, email);
		List<BlogPost> blogPosts1=query.list();
		return blogPosts1;
	}

}
