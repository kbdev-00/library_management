package com.library.dao;

import com.library.model.Book;
import com.library.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

  // 1. Get all books
  public List<Book> getAllBooks() {

    List<Book> books = new ArrayList<>();

    String sql = "SELECT * FROM books ORDER BY book_id";

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {

        Book book = new Book();

        book.setBookId(rs.getInt("book_id"));
        book.setTitle(rs.getString("title"));
        book.setIsbn(rs.getString("isbn"));
        book.setPublisher(rs.getString("publisher"));
        book.setPublicationYear(rs.getInt("publication_year"));
        book.setTotalCopies(rs.getInt("total_copies"));
        book.setAvailableCopies(rs.getInt("available_copies"));
        book.setCategoryId(rs.getInt("category_id"));

        books.add(book);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return books;
  }

  // 2. Add book
  public boolean addBook(Book book) {

    String sql = """
        INSERT INTO books
        (title, isbn, publisher, publication_year,
         total_copies, available_copies, category_id)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setString(1, book.getTitle());
      ps.setString(2, book.getIsbn());
      ps.setString(3, book.getPublisher());
      ps.setInt(4, book.getPublicationYear());
      ps.setInt(5, book.getTotalCopies());
      ps.setInt(6, book.getAvailableCopies());
      ps.setInt(7, book.getCategoryId());

      return ps.executeUpdate() > 0;

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  // 3. Search books
  public List<Book> searchBooks(String keyword) {

    List<Book> books = new ArrayList<>();

    String sql = """
        SELECT *
        FROM books
        WHERE LOWER(title) LIKE LOWER(?)
           OR LOWER(isbn) LIKE LOWER(?)
        ORDER BY book_id
        """;

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

      String searchPattern = "%" + keyword + "%";

      ps.setString(1, searchPattern);
      ps.setString(2, searchPattern);

      try (ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

          Book book = new Book();

          book.setBookId(rs.getInt("book_id"));
          book.setTitle(rs.getString("title"));
          book.setIsbn(rs.getString("isbn"));
          book.setPublisher(rs.getString("publisher"));
          book.setPublicationYear(rs.getInt("publication_year"));
          book.setTotalCopies(rs.getInt("total_copies"));
          book.setAvailableCopies(rs.getInt("available_copies"));
          book.setCategoryId(rs.getInt("category_id"));

          books.add(book);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return books;
  }

  public boolean updateBook(Book book) {

    String sql = """
        UPDATE books
        SET title = ?,
            isbn = ?,
            publisher = ?,
            publication_year = ?,
            total_copies = ?,
            available_copies = ?,
            category_id = ?
        WHERE book_id = ?
        """;

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setString(1, book.getTitle());
      ps.setString(2, book.getIsbn());
      ps.setString(3, book.getPublisher());
      ps.setInt(4, book.getPublicationYear());
      ps.setInt(5, book.getTotalCopies());
      ps.setInt(6, book.getAvailableCopies());
      ps.setInt(7, book.getCategoryId());
      ps.setInt(8, book.getBookId());

      return ps.executeUpdate() > 0;

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean deleteBook(int bookId) {

    String sql = "DELETE FROM books WHERE book_id = ?";

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setInt(1, bookId);

      return ps.executeUpdate() > 0;

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public List<Book> searchBooksWithDetails(String keyword) {

    List<Book> books = new ArrayList<>();

    String sql = """
        SELECT DISTINCT
            b.book_id,
            b.title,
            b.isbn,
            b.publisher,
            b.publication_year,
            b.total_copies,
            b.available_copies,
            b.category_id
        FROM books b
        JOIN categories c
            ON b.category_id = c.category_id
        JOIN book_authors ba
            ON b.book_id = ba.book_id
        JOIN authors a
            ON ba.author_id = a.author_id
        WHERE LOWER(b.title) LIKE LOWER(?)
           OR LOWER(b.isbn) LIKE LOWER(?)
           OR LOWER(a.author_name) LIKE LOWER(?)
           OR LOWER(c.category_name) LIKE LOWER(?)
        ORDER BY b.book_id
        """;

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

      String searchPattern = "%" + keyword + "%";

      ps.setString(1, searchPattern);
      ps.setString(2, searchPattern);
      ps.setString(3, searchPattern);
      ps.setString(4, searchPattern);

      try (ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

          Book book = new Book();

          book.setBookId(rs.getInt("book_id"));
          book.setTitle(rs.getString("title"));
          book.setIsbn(rs.getString("isbn"));
          book.setPublisher(rs.getString("publisher"));
          book.setPublicationYear(
              rs.getInt("publication_year"));
          book.setTotalCopies(
              rs.getInt("total_copies"));
          book.setAvailableCopies(
              rs.getInt("available_copies"));
          book.setCategoryId(
              rs.getInt("category_id"));

          books.add(book);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return books;
  }

  // Update book
  public boolean updateBook(Book book) {

    String sql = "UPDATE books SET title = ?, isbn = ?, publisher = ?, " +
        "publication_year = ?, total_copies = ?, available_copies = ? " +
        "WHERE book_id = ?";

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setString(1, book.getTitle());
      ps.setString(2, book.getIsbn());
      ps.setString(3, book.getPublisher());
      ps.setInt(4, book.getPublicationYear());
      ps.setInt(5, book.getTotalCopies());
      ps.setInt(6, book.getAvailableCopies());
      ps.setInt(7, book.getBookId());

      return ps.executeUpdate() > 0;

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  // Delete book
  public boolean deleteBook(int bookId) {

    String sql = "DELETE FROM books WHERE book_id = ?";

    try (
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {

      ps.setInt(1, bookId);

      return ps.executeUpdate() > 0;

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}