package com.library.service;

import com.library.dao.BookDAO;
import com.library.model.Book;

import java.util.List;

public class BookService {

  private final BookDAO bookDAO;

  public BookService() {
    this.bookDAO = new BookDAO();
  }

  // Get all books
  public List<Book> getAllBooks() {

    return bookDAO.getAllBooks();
  }

  // Add book with validation
  public boolean addBook(Book book) {

    if (book == null) {
      return false;
    }

    if (book.getTitle() == null ||
        book.getTitle().trim().isEmpty()) {

      System.out.println("Book title is required.");
      return false;
    }

    if (book.getIsbn() == null ||
        book.getIsbn().trim().isEmpty()) {

      System.out.println("ISBN is required.");
      return false;
    }

    if (book.getTotalCopies() <= 0) {

      System.out.println(
          "Total copies must be greater than 0.");

      return false;
    }

    if (book.getAvailableCopies() < 0 ||
        book.getAvailableCopies() > book.getTotalCopies()) {

      System.out.println(
          "Invalid available copies.");

      return false;
    }

    return bookDAO.addBook(book);
  }

  // Update book
  public boolean updateBook(Book book) {

    if (book == null || book.getBookId() <= 0) {
      return false;
    }

    if (book.getTitle() == null ||
        book.getTitle().trim().isEmpty()) {

      System.out.println("Book title is required.");
      return false;
    }

    if (book.getTotalCopies() <= 0) {
      return false;
    }

    if (book.getAvailableCopies() < 0 ||
        book.getAvailableCopies() > book.getTotalCopies()) {

      return false;
    }

    return bookDAO.updateBook(book);
  }

  // Delete book
  public boolean deleteBook(int bookId) {

    if (bookId <= 0) {
      return false;
    }

    return bookDAO.deleteBook(bookId);
  }

  // Search books
  public List<Book> searchBooks(String keyword) {

    if (keyword == null ||
        keyword.trim().isEmpty()) {

      return bookDAO.getAllBooks();
    }

    return bookDAO.searchBooks(
        keyword.trim());
  }
}