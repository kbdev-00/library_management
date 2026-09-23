package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;

import java.util.List;

public class TestAdvancedSearch {

  public static void main(String[] args) {

    BookDAO bookDAO = new BookDAO();

    String keyword = "programming";

    List<Book> books = bookDAO.searchBooksWithDetails(keyword);

    if (books.isEmpty()) {
      System.out.println("No books found.");
      return;
    }

    for (Book book : books) {

      System.out.println(
          book.getBookId() + " | " +
              book.getTitle() + " | " +
              book.getIsbn() + " | " +
              book.getAvailableCopies());
    }
  }
}