package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;

public class TestAddBook {

  public static void main(String[] args) {

    Book book = new Book();

    book.setTitle("Java Programming Masterclass");
    book.setIsbn("9781234567890");
    book.setPublisher("Tech Publications");
    book.setPublicationYear(2025);
    book.setTotalCopies(5);
    book.setAvailableCopies(5);
    book.setCategoryId(1);

    BookDAO bookDAO = new BookDAO();

    boolean result = bookDAO.addBook(book);

    if (result) {
      System.out.println("Book added successfully!");
    } else {
      System.out.println("Failed to add book.");
    }
  }
}