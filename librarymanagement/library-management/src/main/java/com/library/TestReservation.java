package com.library;

import com.library.dao.ReservationDAO;

public class TestReservation {

  public static void main(String[] args) {

    ReservationDAO reservationDAO = new ReservationDAO();

    int bookId = 1;
    int userId = 3;

    boolean result = reservationDAO.reserveBook(bookId, userId);

    if (result) {
      System.out.println(
          "Reservation completed.");
    } else {
      System.out.println(
          "Reservation failed.");
    }
  }
}