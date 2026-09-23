package com.library;

import com.library.dao.FineDAO;

public class TestPayFine {

  public static void main(String[] args) {

    FineDAO fineDAO = new FineDAO();

    int fineId = 1;

    boolean result = fineDAO.payFine(fineId);

    if (result) {
      System.out.println("Fine paid successfully.");
    } else {
      System.out.println(
          "Fine not found or already paid.");
    }
  }
}