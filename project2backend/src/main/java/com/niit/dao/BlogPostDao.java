package com.niit.dao;

import java.util.List;

import com.niit.models.BlogPost;

public interface BlogPostDao {
void addBlogPost(BlogPost blogPost);
List<BlogPost> getBlogsWaitingForApproval();
List<BlogPost> getBlogsApproved();
BlogPost getBlog(int blogId);
void approveBlogPost(int blogPostId);
void rejectBlogPost(int blogPostId);
void updateBlogPost(BlogPost blogPost);
List<BlogPost> getBlogByEmail(String user_email);
List<BlogPost> getBlogByEmail1(String email);
}