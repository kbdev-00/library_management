package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;

public class TestUpdateBook {

  public static void main(String[] args) {

    Book book = new Book();

    book.setBookId(1);
    book.setTitle("Effective Java - Updated");
    book.setIsbn("9780134685991");
    book.setPublisher("Addison-Wesley");
    book.setPublicationYear(2018);
    book.setTotalCopies(6);
    book.setAvailableCopies(6);
    book.setCategoryId(1);

    BookDAO bookDAO = new BookDAO();

    boolean result = bookDAO.updateBook(book);

    if (result) {
      System.out.println("Book updated successfully!");
    } else {
      System.out.println("Book update failed.");
    }
  }
}