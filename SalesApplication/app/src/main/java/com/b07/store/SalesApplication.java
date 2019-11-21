package com.b07.store;

import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.exceptions.UserCreationException;
import com.b07.inventory.Inventory;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.Roles;
import com.b07.users.User;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;

public class SalesApplication {

  /**
   * This is the main method to run your entire program! Follow the "Pulling it together"
   * instructions to finish this off.
   *
   * @param argv unused.
   */
  public static void main(String[] argv) {
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    if (connection == null) {
      System.out.print("Database connection failed");
    }
    try {

      if (argv.length != 0 && argv[0].equals("-1")) {
        DatabaseDriverExtender.initialize(connection);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int userId = -1;

        System.out.println("Create an admin account.");
        while (userId == -1) {
          try {
            userId = AccountCreation.createAccount(bufferedReader, Roles.ADMIN.name());
          } catch (UserCreationException ex) {
            System.out.println("There was an error creating the user account. Please try again.");
          }
        }
        System.out.println("Your userid is " + userId + ". Please remember it.");

        userId = -1;
        System.out.println("Create an employee account.");
        while (userId == -1) {
          try {
            userId = AccountCreation.createAccount(bufferedReader, Roles.EMPLOYEE.name());
          } catch (UserCreationException ex) {
            System.out.println("There was an error creating the user account. Please try again.");
          }
        }
        System.out.println("Your userid is " + userId + ". Please remember it.");

        bufferedReader.close();
        System.out.println("Database initialized. Admin and employee account created.");
      } else if (argv.length != 0 && argv[0].equals("1")) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        AdminUI.adminInterface(bufferedReader);
        bufferedReader.close();
      } else {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";

        while (!userInput.equals("0")) {
          System.out.println("1 - Employee Login");
          System.out.println("2 - Customer Login");
          System.out.println("0 - Exit");
          System.out.println("Enter Selection:");

          userInput = bufferedReader.readLine().trim();
          int userId;

          switch (userInput) {
            case "1":
              userId = StoreAuthentication.authenticationInterface(bufferedReader,
                  Roles.EMPLOYEE.name());
              if (userId != -1) {
                System.out.println("Authenticated!");
                Inventory inventory = DatabaseSelectHelper.getInventory();
                User user = DatabaseSelectHelper.getUserDetails(userId);
                Employee employee = new Employee(user.getId(), user.getName(), user.getAge(),
                    user.getAddress(), true);
                EmployeeInterface employeeInterface = new EmployeeInterface(employee, inventory);
                EmployeeUI.employeeInterface(bufferedReader, employeeInterface);

              } else {
                System.out.println("The userId and password do not match our employee database.");
              }
              break;
            case "2":
              userId = StoreAuthentication.authenticationInterface(bufferedReader,
                  Roles.CUSTOMER.name());
              if (userId != -1) {
                System.out.println("Authenticated.");
                User user = DatabaseSelectHelper.getUserDetails(userId);
                Customer customer = new Customer(user.getId(), user.getName(), user.getAge(),
                    user.getAddress(), true);
                ShoppingCart shoppingCart = new ShoppingCart(customer);
                CustomerUI.shoppingCartInterface(bufferedReader, shoppingCart);

              } else {
                System.out.println("The userId and password do not match our employee database.");
              }
              break;
            case "0":
              break;
            default:
              System.out.println("Choose a number from the menu.");
          }
        }
        bufferedReader.close();
        System.out.println("All done!:)");
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("Something went wrong :(");
    } finally {
      try {
        connection.close();
      } catch (Exception e) {
        System.out.println("Looks like it was closed already :)");
      }
    }
  }
}

