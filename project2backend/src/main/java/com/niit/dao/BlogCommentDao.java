package com.niit.dao;

import java.util.List;

import com.niit.models.BlogComment;
import com.niit.models.BlogPost;
import com.niit.models.User;

public interface BlogCommentDao {
void addBlogComment(BlogComment blogComment);
List<BlogComment> getAllBlogComments(int blogPostId);
void updateBlogComment(BlogComment blogComment);
void deleteBlogComment(int commentId);
BlogComment getBlogCommentById(int blogCommentId);
}