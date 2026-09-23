package com.library;

import com.library.service.IssueService;

public class TestReturnService {

  public static void main(String[] args) {

    IssueService issueService = new IssueService();

    int issueId = 5;

    boolean result = issueService.returnBook(issueId);

    if (result) {
      System.out.println(
          "Book returned through Service Layer.");
    } else {
      System.out.println(
          "Book return failed.");
    }
  }
}