package com.niit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.dao.BookDao;
import com.niit.models.Book;
import com.niit.models.ErrorClazz;

@RestController
public class BookController {
	@Autowired
private BookDao bookDao;
	
public BookController(){
	System.out.println("BookController bean is created");
}
//@ResponseBody - convert java object to JSON
//@ResponseBody - message convertion , List<Book> to Array of json objects
//after converting, add the data in the body of HttpResponse object
/*@RequestMapping(value="/getallbooks",method=RequestMethod.GET)
public @ResponseBody  List<Book> getAllBooks(){
	List<Book> books=bookDao.getAllBooks();
	return books;
}*/
@RequestMapping(value="/getallbooks",method=RequestMethod.GET)
public ResponseEntity <?> getAllBooks(){
	List<Book> books=bookDao.getAllBooks();
	if(books.isEmpty())
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	else
		return new ResponseEntity<List<Book>>(books,HttpStatus.OK);
}
@RequestMapping(value="/addbook",method=RequestMethod.POST)
public /*@ResponseBody*/ ResponseEntity <?> addBook(@RequestBody Book book){
	try{
		bookDao.addBook(book);
	}
	catch(Exception e)
	{
		ErrorClazz errorClazz=new ErrorClazz(1,"UNABLE TO INSERT BOOK DETAILS"+e.getMessage());
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return new ResponseEntity<Book>(book,HttpStatus.OK);
}
@RequestMapping(value="/getbook",method=RequestMethod.GET)
public /*@ResponseBody*/ ResponseEntity <?>  getBook(@RequestParam int isbn){
	Book book=bookDao.getBook(isbn);
	if(book == null)
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	else
	return new ResponseEntity<Book>(book,HttpStatus.OK);
}
@RequestMapping(value="/updatebook",method=RequestMethod.PUT)
public /*@ResponseBody*/ ResponseEntity <?> updateBook(@RequestBody Book book){
	try{
		bookDao.updateBook(book);
	}
	catch(Exception e)
	{
		ErrorClazz errorClazz=new ErrorClazz(2,"UNABLE TO UPDATE BOOK DETAILS"+e.getMessage());
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	return new ResponseEntity<Book>(book,HttpStatus.OK);
}
@RequestMapping(value="/deletebook",method=RequestMethod.DELETE)
public /*@ResponseBody*/ ResponseEntity <?> deleteBook(@RequestParam int isbn){
	try{
		bookDao.deleteBook(isbn);
	}
	catch(Exception e)
	{
		ErrorClazz errorClazz=new ErrorClazz(3,"UNABLE TO DELETE BOOK DETAILS"+e.getMessage());
		return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	ErrorClazz errorClazz=new ErrorClazz(4,"BOOK RECORD HAS BEEN DELETED");
	return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.OK);
}
}