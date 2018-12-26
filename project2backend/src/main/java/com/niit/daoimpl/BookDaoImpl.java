package com.niit.daoimpl;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.dao.BookDao;
import com.niit.models.Book;
@Repository
@Transactional
public class BookDaoImpl implements BookDao{
	@Autowired
	private   SessionFactory sessionFactory;

		public BookDaoImpl(){
			System.out.println("BookDaoImpl bean is created");
		}
		public List<Book> getAllBooks() {
			Session session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("from Book");
			List<Book> books=query.list();
			return books;
		}
		public void addBook(Book book) {
			Session session=sessionFactory.getCurrentSession();
			session.save(book);
		}
		public Book getBook(int isbn) {
			Session session=sessionFactory.getCurrentSession();
			Book book=(Book)session.get(Book.class,isbn);
			return book;
		}
		public void updateBook(Book book) {
			Session session=sessionFactory.getCurrentSession();
			session.update(book);
		}
		public void deleteBook(int isbn) {
			Session session=sessionFactory.getCurrentSession();
			Book book=(Book)session.get(Book.class,isbn);
			session.delete(book);
			
		}

}
