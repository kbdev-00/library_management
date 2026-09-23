package com.library;

import com.library.dao.ReservationDAO;

public class TestReservationFulfillment {

  public static void main(String[] args) {

    ReservationDAO reservationDAO = new ReservationDAO();

    int bookId = 1;

    boolean result = reservationDAO.fulfillNextReservation(bookId);

    if (result) {
      System.out.println(
          "Reservation fulfillment completed.");
    } else {
      System.out.println(
          "No reservation was fulfilled.");
    }
  }
}