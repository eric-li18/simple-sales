package com.b07.store;

import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.inventory.Item;
import com.b07.users.Admin;
import com.b07.users.Employee;
import com.b07.users.Roles;
import com.b07.users.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminUI {

  public static void adminInterface(BufferedReader bufferedReader) throws IOException {
    User user;
    int userId = -1;
    String userInput = "";

    System.out.println("Admin login");
    while (userId == -1) {
      userId = StoreAuthentication.authenticationInterface(bufferedReader, Roles.ADMIN.name());
      if (userId == -1) {
        System.out.println("The userId and password do not match our database.");
      }
    }

    user = DatabaseSelectHelper.getUserDetails(userId);
    Admin admin =
        new Admin(user.getId(), user.getName(), user.getAge(), user.getAddress(), true);
    System.out.println("Authenticated.");

    while (!userInput.equals("3")) {
      System.out.println("1. Promote employee");
      System.out.println("2. View sales log");
      System.out.println("3. Exit");

      userInput = bufferedReader.readLine().trim();

      switch (userInput) {
        case "1":
          System.out.println("Enter the userId of the employee to promote or 0 to exit.");
          List<Integer> roleIds = DatabaseSelectHelper.getRoleIds();
          List<Integer> userIds = new ArrayList<>();

          for (int roleId : roleIds) {
            if (DatabaseSelectHelper.getRoleName(roleId).equals(Roles.EMPLOYEE.name())) {
              userIds = DatabaseSelectHelper.getUsersByRole(roleId);

              for (int id : userIds) {
                user = DatabaseSelectHelper.getUserDetails(id);
                System.out.println(user.getId() + " - " + user.getName());
              }
            }
          }

          int employeeId = -1;
          boolean validEmployee = false;
          while (!validEmployee) {
            try {
              employeeId = Integer.parseInt(bufferedReader.readLine().trim());
              validEmployee = userIds.contains(employeeId) || employeeId == 0;
            } catch (NumberFormatException ex) {
              System.out.println("Please enter a userId from the above list or 0 to exit.");
            }
            if (!validEmployee) {
              System.out.println("Please enter a userId from the above list or 0 to exit.");
            }
          }

          if (employeeId == 0) {
            break;
          }

          User employee = DatabaseSelectHelper.getUserDetails(employeeId);
          admin.promoteEmployee((Employee) employee);
          Admin promotedEmployee = new Admin(employee.getId(), employee.getName(),
              employee.getAge(), employee.getAddress());
          employee = null;
          System.out.println(promotedEmployee.getName() + " has been promoted to admin.");

          break;

        case "2":
          SalesLog salesLog = DatabaseSelectHelper.getSales();
          List<Sale> sales = salesLog.getLog();
          for (Sale sale : sales) {
            System.out.println("Customer: " + sale.getUser().getName());
            System.out.println("Purchase Number: " + sale.getId());
            System.out.println("Total Purchase Price: " + sale.getTotalPrice());
            System.out.println("Itemized Breakdown: ");
            HashMap<Item, Integer> itemMap = sale.getItemMap();
            for (Item item : itemMap.keySet()) {
              System.out.println(item.getName() + ": " + itemMap.get(item));
            }
            System.out.println("----------------------------------------");
          }

          HashMap<Item, Integer> totalItemMap = salesLog.getTotalItemMap();
          for (Item item : totalItemMap.keySet()) {
            System.out.println("Number " + item.getName() + " Sold: " + totalItemMap.get(item));
          }

          System.out.println("TOTAL SALES: " + salesLog.getTotalSales());
          break;

        case "3":
          break;

        default:
          System.out.println("Choose a number from the menu.");
      }
    }
  }
}