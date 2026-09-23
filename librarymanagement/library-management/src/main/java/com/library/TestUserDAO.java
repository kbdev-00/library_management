package com.library;

import com.library.dao.UserDAO;
import com.library.model.User;

import java.util.List;

public class TestUserDAO {

  public static void main(String[] args) {

    UserDAO userDAO = new UserDAO();

    List<User> users = userDAO.getAllUsers();

    if (users.isEmpty()) {
      System.out.println("No users found.");
      return;
    }

    for (User user : users) {

      System.out.println(
          user.getUserId() + " | " +
              user.getName() + " | " +
              user.getEmail() + " | " +
              user.getRole() + " | " +
              user.getStatus());
    }
  }
}