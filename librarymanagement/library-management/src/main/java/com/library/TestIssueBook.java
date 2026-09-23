package com.library;

import com.library.dao.IssueDAO;

public class TestIssueBook {

  public static void main(String[] args) {

    IssueDAO issueDAO = new IssueDAO();

    int bookId = 7;
    int userId = 3;

    boolean result = issueDAO.issueBook(bookId, userId);

    if (result) {
      System.out.println("Issue operation completed.");
    } else {
      System.out.println("Issue operation failed.");
    }
  }
}