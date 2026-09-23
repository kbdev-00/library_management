package com.library;

import com.library.model.Book;
import com.library.service.BookService;

public class TestBookService {

  public static void main(String[] args) {

    BookService bookService = new BookService();

    Book book = new Book();

    book.setTitle("Design Patterns");
    book.setIsbn("9780201633610");
    book.setPublisher("Addison-Wesley");
    book.setPublicationYear(1994);
    book.setTotalCopies(5);
    book.setAvailableCopies(5);
    book.setCategoryId(1);

    boolean result = bookService.addBook(book);

    if (result) {
      System.out.println(
          "Book added through Service Layer.");
    } else {
      System.out.println(
          "Book could not be added.");
    }
  }
}