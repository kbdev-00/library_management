package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;

import java.util.List;

public class TestBookDAO {

  public static void main(String[] args) {

    BookDAO bookDAO = new BookDAO();

    List<Book> books = bookDAO.getAllBooks();

    for (Book book : books) {

      System.out.println(
          book.getBookId() + " | " +
              book.getTitle() + " | " +
              book.getIsbn() + " | " +
              book.getAvailableCopies());
    }
  }
}