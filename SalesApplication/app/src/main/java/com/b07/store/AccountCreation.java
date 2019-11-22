package com.b07.store;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import com.b07.database.DatabaseInsertHelper;
import com.b07.exceptions.UserCreationException;
import com.b07.validation.Validator;

public class AccountCreation {

  /**
   * Method to prompt the user for all the user info and return a list containing it
   *
   * @param bufferedReader the scanner used to parse the input
   * @return a list of the user info with name, age, address, password stored at the 0-3 indices
   *         respectively
   */
  public static int createAccount(BufferedReader bufferedReader, String role, Context context)
      throws UserCreationException, IOException {
    if (!Validator.validateRoleName(role)) {
      System.out.println("Input a valid role name.");
      throw new UserCreationException();
    }

    boolean valid = false;
    String name = null;
    System.out.println("Enter your first and last name: ");
    while (!valid) {
      name = bufferedReader.readLine().trim();
      valid = Validator.validateUserName(name);
      if (!valid) {
        System.out.println("Enter a valid name:");
      }
    }

    valid = false;
    int age = -1;
    System.out.println("Enter your age: ");
    while (!valid) {
      try {
        age = Integer.parseInt(bufferedReader.readLine().trim());
        valid = Validator.validateAge(age);
        if (!valid) {
          System.out.println("Input a valid age: ");
        }
      } catch (NumberFormatException ex) {
        System.out.println("Input a valid age: ");
      }
    }

    valid = false;
    String address = null;
    System.out.println("Enter your address: ");
    while (!valid) {
      address = bufferedReader.readLine().trim();
      valid = Validator.validateAddress(address);
      if (!valid) {
        System.out.println("Input a valid address: ");
      }
    }

    String password;
    String password2;
    do {
      System.out.println("Enter your password: ");
      password = bufferedReader.readLine();

      System.out.println("Re-enter your password: ");
      password2 = bufferedReader.readLine();

      if (!password.equals(password2)) {
        System.out.println("Your passwords do not match.");
      }
    } while (!password.equals(password2));

    int userId = DatabaseInsertHelper.insertNewUser(name, age, address, password, context);
    if (userId == -1) {
      throw new UserCreationException();
    }
    int roleId = DatabaseInsertHelper.insertRole(role, context);
    if (roleId == -1) {
      throw new UserCreationException();
    }
    int userRoleId = DatabaseInsertHelper.insertUserRole(userId, roleId,context);
    if (userRoleId == -1) {
      throw new UserCreationException();
    }
    return userId;
  }
}

