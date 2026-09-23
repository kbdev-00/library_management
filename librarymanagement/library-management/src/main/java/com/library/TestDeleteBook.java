package com.library;

import com.library.dao.BookDAO;

public class TestDeleteBook {

  public static void main(String[] args) {

    BookDAO bookDAO = new BookDAO();

    int bookId = 6;

    boolean result = bookDAO.deleteBook(bookId);

    if (result) {
      System.out.println("Book deleted successfully!");
    } else {
      System.out.println("Book not found.");
    }
  }
}