package com.library.dao;

import com.library.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReservationDAO {

  public boolean reserveBook(int bookId, int userId) {

    String checkBookSql = """
        SELECT available_copies
        FROM books
        WHERE book_id = ?
        """;

    String checkReservationSql = """
        SELECT reservation_id
        FROM reservations
        WHERE book_id = ?
          AND user_id = ?
          AND status = 'WAITING'
        """;

    String insertSql = """
        INSERT INTO reservations
        (book_id, user_id, reservation_date, status)
        VALUES (?, ?, CURRENT_TIMESTAMP, 'WAITING')
        """;

    try (Connection con = DBConnection.getConnection()) {

      // 1. Check book
      try (PreparedStatement ps = con.prepareStatement(checkBookSql)) {

        ps.setInt(1, bookId);

        try (ResultSet rs = ps.executeQuery()) {

          if (!rs.next()) {
            System.out.println("Book not found.");
            return false;
          }

          int available = rs.getInt("available_copies");

          if (available > 0) {
            System.out.println(
                "Book is currently available. "
                    + "Reservation is not required.");
            return false;
          }
        }
      }

      // 2. Prevent duplicate reservation
      try (PreparedStatement ps = con.prepareStatement(checkReservationSql)) {

        ps.setInt(1, bookId);
        ps.setInt(2, userId);

        try (ResultSet rs = ps.executeQuery()) {

          if (rs.next()) {
            System.out.println(
                "You already have a reservation.");
            return false;
          }
        }
      }

      // 3. Create reservation
      try (PreparedStatement ps = con.prepareStatement(insertSql)) {

        ps.setInt(1, bookId);
        ps.setInt(2, userId);

        ps.executeUpdate();
      }

      System.out.println("Book reserved successfully.");

      return true;

    } catch (Exception e) {

      e.printStackTrace();
      return false;
    }
  }

  public boolean fulfillNextReservation(int bookId) {

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

    try (Connection con = DBConnection.getConnection()) {

      int reservationId;
      int userId;

      // 1. Find the oldest waiting reservation
      try (PreparedStatement ps = con.prepareStatement(findReservationSql)) {

        ps.setInt(1, bookId);

        try (ResultSet rs = ps.executeQuery()) {

          if (!rs.next()) {
            System.out.println(
                "No waiting reservation found.");
            return false;
          }

          reservationId = rs.getInt("reservation_id");

          userId = rs.getInt("user_id");
        }
      }

      // 2. Change WAITING → READY
      try (PreparedStatement ps = con.prepareStatement(updateReservationSql)) {

        ps.setInt(1, reservationId);

        ps.executeUpdate();
      }

      System.out.println(
          "Reservation #" + reservationId +
              " is now READY for user #" + userId);

      return true;

    } catch (Exception e) {

      e.printStackTrace();
      return false;
    }
  }
}