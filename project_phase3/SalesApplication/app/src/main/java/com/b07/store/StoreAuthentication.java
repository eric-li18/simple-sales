package com.b07.store;

import android.content.Context;
import com.b07.database.DatabaseSelectHelper;
import com.b07.users.Roles;
import com.b07.users.User;
import java.io.BufferedReader;
import java.io.IOException;

public class StoreAuthentication {

  /**
   * Method to give an authentication interface for all the users
   *
   * @param bufferedReader the BufferedReader
   * @param role           the role to authenticate
   * @return the userId if authenticated, -1 if not authenticated
   * @throws IOException if there is an exception thrown by bufferedReader
   */
  public static int authenticationInterface(BufferedReader bufferedReader, String role, Context context)
      throws IOException {
    User user;
    boolean authenticated;
    int userId = -1;
    int roleId;
    String password;
    String roleName;

    System.out.println("Enter your userId:");
    while (userId == -1) {
      try {
        userId = Integer.parseInt(bufferedReader.readLine().trim());
      } catch (NumberFormatException ex) {
        System.out.println("Enter a valid userId");
      }
    }
    System.out.println("Enter your password:");
    password = bufferedReader.readLine();

    roleId = DatabaseSelectHelper.getUserRoleId(userId, context);
    roleName = DatabaseSelectHelper.getRoleName(roleId, context);

    if (roleName == null || roleId == -1) {
      System.out.println("You are not in the system");
      return -1;
    }

    if (role.contentEquals(Roles.ADMIN.name())) {
      if (!roleName.contentEquals(Roles.ADMIN.name())) {
        System.out.println("You are not an admin.");
        return -1;
      }
    } else if (role.equals(Roles.EMPLOYEE.name())) {
      if (!roleName.equals(Roles.EMPLOYEE.name())) {
        System.out.println("You are not an employee.");
        return -1;
      }
    } else if (role.equals(Roles.CUSTOMER.name())) {
      if (!roleName.equals(Roles.CUSTOMER.name())) {
        System.out.println("You are not an customer.");
        return -1;
      }
    }

    user = DatabaseSelectHelper.getUserDetails(userId, context);
    authenticated = user.authenticate(password, context);
    if (authenticated) {
      return userId;
    } else {
      return -1;
    }
  }
}
