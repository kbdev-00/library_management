package com.library;

import com.library.service.IssueService;

public class TestIssueService {

  public static void main(String[] args) {

    IssueService issueService = new IssueService();

    int bookId = 7;
    int userId = 3;

    boolean result = issueService.issueBook(bookId, userId);

    if (result) {
      System.out.println(
          "Book issued through Service Layer.");
    } else {
      System.out.println(
          "Book issue failed.");
    }
  }
}