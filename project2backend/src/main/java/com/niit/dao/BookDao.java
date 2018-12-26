package com.niit.dao;

import java.util.List;

import com.niit.models.Book;

public interface BookDao {
List<Book> getAllBooks();

void addBook(Book book);

Book getBook(int isbn);

void updateBook(Book book);

void deleteBook(int isbn);
}