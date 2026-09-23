package com.library;

import com.library.dao.IssueDAO;

public class TestReturnBook {

  public static void main(String[] args) {

    IssueDAO issueDAO = new IssueDAO();

    int issueId = 6;

    boolean result = issueDAO.returnBook(issueId);

    if (result) {
      System.out.println(
          "Return operation completed.");
    } else {
      System.out.println(
          "Return operation failed.");
    }
  }
}