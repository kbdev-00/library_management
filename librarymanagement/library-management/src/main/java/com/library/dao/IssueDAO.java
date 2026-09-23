package com.library.dao;

import com.library.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class IssueDAO {

  public boolean issueBook(int bookId, int userId) {

    String checkBookSql = "SELECT available_copies FROM books WHERE book_id = ?";

    String issueSql = """
        INSERT INTO issue_records
        (book_id, user_id, issue_date, due_date, status)
        VALUES (?, ?, CURRENT_DATE, CURRENT_DATE + 14, 'ISSUED')
        """;

    String updateBookSql = """
        UPDATE books
        SET available_copies = available_copies - 1
        WHERE book_id = ?
        """;

    Connection con = null;

    try {

      con = DBConnection.getConnection();

      // Start transaction
      con.setAutoCommit(false);

      // 1. Check availability
      try (PreparedStatement ps = con.prepareStatement(checkBookSql)) {

        ps.setInt(1, bookId);

        try (ResultSet rs = ps.executeQuery()) {

          if (!rs.next()) {
            System.out.println("Book not found.");
            con.rollback();
            return false;
          }

          int availableCopies = rs.getInt("available_copies");

          if (availableCopies <= 0) {
            System.out.println(
                "Book is not available.");
            con.rollback();
            return false;
          }
        }
      }

      // 2. Create issue record
      try (PreparedStatement ps = con.prepareStatement(issueSql)) {

        ps.setInt(1, bookId);
        ps.setInt(2, userId);

        ps.executeUpdate();
      }

      // 3. Decrease available copies
      try (PreparedStatement ps = con.prepareStatement(updateBookSql)) {

        ps.setInt(1, bookId);

        ps.executeUpdate();
      }

      // 4. Commit everything
      con.commit();

      System.out.println("Book issued successfully.");

      return true;

    } catch (Exception e) {

      e.printStackTrace();

      try {
        if (con != null) {
          con.rollback();
        }
      } catch (Exception rollbackException) {
        rollbackException.printStackTrace();
      }

      return false;

    } finally {

      try {
        if (con != null) {
          con.setAutoCommit(true);
          con.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public boolean returnBook(int issueId) {

    String findIssueSql = """
        SELECT book_id, due_date, status
        FROM issue_records
        WHERE issue_id = ?
        """;

    String updateIssueSql = """
        UPDATE issue_records
        SET return_date = CURRENT_DATE,
            status = 'RETURNED'
        WHERE issue_id = ?
        """;

    String updateBookSql = """
        UPDATE books
        SET available_copies = available_copies + 1
        WHERE book_id = ?
        """;

    String insertFineSql = """
        INSERT INTO fines
        (issue_id, amount, paid_status)
        VALUES (?, ?, 'UNPAID')
        """;

    String findReservationSql = """
        SELECT reservation_id, user_id
        FROM reservations
        WHERE book_id = ?
          AND status = 'WAITING'
        ORDER BY reservation_date
        LIMIT 1
        """;

    String updateReservationSql = """
        UPDATE reservations
        SET status = 'READY'
        WHERE reservation_id = ?
        """;

    Connection con = null;

    try {

      con = DBConnection.getConnection();

      // Start transaction
      con.setAutoCommit(false);

      int bookId;
      java.sql.Date dueDate;
      String status;

      // ------------------------------------------------
      // 1. Find issue record
      // ------------------------------------------------

      try (PreparedStatement ps = con.prepareStatement(findIssueSql)) {

        ps.setInt(1, issueId);

        try (ResultSet rs = ps.executeQuery()) {

          if (!rs.next()) {

            System.out.println(
                "Issue record not found.");

            con.rollback();
            return false;
          }

          bookId = rs.getInt("book_id");
          dueDate = rs.getDate("due_date");
          status = rs.getString("status");

          if ("RETURNED".equals(status)) {

            System.out.println(
                "Book has already been returned.");

            con.rollback();
            return false;
          }
        }
      }

      // ------------------------------------------------
      // 2. Mark issue as RETURNED
      // ------------------------------------------------

      try (PreparedStatement ps = con.prepareStatement(updateIssueSql)) {

        ps.setInt(1, issueId);

        ps.executeUpdate();
      }

      // ------------------------------------------------
      // 3. Increase available copies
      // ------------------------------------------------

      try (PreparedStatement ps = con.prepareStatement(updateBookSql)) {

        ps.setInt(1, bookId);

        ps.executeUpdate();
      }

      // ------------------------------------------------
      // 4. Calculate fine
      // ------------------------------------------------

      java.sql.Date returnDate = java.sql.Date.valueOf(
          java.time.LocalDate.now());

      long overdueDays = 0;

      if (returnDate.after(dueDate)) {

        long milliseconds = returnDate.getTime()
            - dueDate.getTime();

        overdueDays = milliseconds /
            (1000 * 60 * 60 * 24);
      }

      double fineAmount = overdueDays * 5.0;

      if (fineAmount > 0) {

        try (PreparedStatement ps = con.prepareStatement(insertFineSql)) {

          ps.setInt(1, issueId);
          ps.setDouble(2, fineAmount);

          ps.executeUpdate();
        }

        System.out.println(
            "Fine generated: ₹" + fineAmount);

      } else {

        System.out.println("No fine.");
      }

      // ------------------------------------------------
      // 5. Find oldest reservation
      // ------------------------------------------------

      int reservationId = -1;
      int reservationUserId = -1;

      try (PreparedStatement ps = con.prepareStatement(findReservationSql)) {

        ps.setInt(1, bookId);

        try (ResultSet rs = ps.executeQuery()) {

          if (rs.next()) {

            reservationId = rs.getInt("reservation_id");

            reservationUserId = rs.getInt("user_id");
          }
        }
      }

      // ------------------------------------------------
      // 6. Mark reservation READY
      // ------------------------------------------------

      if (reservationId != -1) {

        try (PreparedStatement ps = con.prepareStatement(
            updateReservationSql)) {

          ps.setInt(1, reservationId);

          ps.executeUpdate();
        }

        System.out.println(
            "Reservation #" +
                reservationId +
                " is READY for user #" +
                reservationUserId);
      }

      // ------------------------------------------------
      // 7. Commit everything
      // ------------------------------------------------

      con.commit();

      System.out.println(
          "Book returned successfully.");

      return true;

    } catch (Exception e) {

      e.printStackTrace();

      try {

        if (con != null) {
          con.rollback();
        }

      } catch (Exception rollbackException) {

        rollbackException.printStackTrace();
      }

      return false;

    } finally {

      try {

        if (con != null) {

          con.setAutoCommit(true);
          con.close();
        }

      } catch (Exception e) {

        e.printStackTrace();
      }
    }
  }
}