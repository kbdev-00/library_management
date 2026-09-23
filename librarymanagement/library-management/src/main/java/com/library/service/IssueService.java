package com.library.service;

import com.library.dao.IssueDAO;

public class IssueService {

  private final IssueDAO issueDAO;

  public IssueService() {
    this.issueDAO = new IssueDAO();
  }

  // Issue a book
  public boolean issueBook(int bookId, int userId) {

    if (bookId <= 0) {
      System.out.println("Invalid book ID.");
      return false;
    }

    if (userId <= 0) {
      System.out.println("Invalid user ID.");
      return false;
    }

    return issueDAO.issueBook(bookId, userId);
  }

  // Return a book
  public boolean returnBook(int issueId) {

    if (issueId <= 0) {
      System.out.println("Invalid issue ID.");
      return false;
    }

    return issueDAO.returnBook(issueId);
  }
}