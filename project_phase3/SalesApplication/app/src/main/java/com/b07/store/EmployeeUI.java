package com.b07.store;

import com.b07.validation.Validator;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import com.b07.database.helper.DatabaseInsertHelper;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.exceptions.UserCreationException;
import com.b07.inventory.Item;
import com.b07.inventory.ItemTypes;
import com.b07.users.Customer;
import com.b07.users.Employee;
import com.b07.users.Roles;
import com.b07.users.User;

public class EmployeeUI {

  /**
   * Method to give the employee an interface of the employee interface
   *
   * @param bufferedReader    the BufferedReader
   * @param employeeInterface the EmployeeInterface object
   * @throws IOException if there is an exception thrown by bufferedReader
   */
  public static void employeeInterface(BufferedReader bufferedReader,
      EmployeeInterface employeeInterface) throws IOException {
    String userInput = "";
    int quantity = -1;
    boolean valid = false;

    while (!userInput.equals("7")) {
      int userId = -1;
      int accId = -1;
      User user;

      System.out.println("1. Authenticate new employee");
      System.out.println("2. Make new customer");
      System.out.println("3. Make new account");
      System.out.println("4. Make new employee");
      System.out.println("5. Restock inventory");
      System.out.println("6. Add a new item");
      System.out.println("7. Exit");
      System.out.println("Enter selection: ");

      userInput = bufferedReader.readLine().trim();

      switch (userInput) {
        case "1":
          userId =
              StoreAuthentication.authenticationInterface(bufferedReader, Roles.EMPLOYEE.name());
          if (userId != -1) {
            System.out.println("Authenticated.");
            user = DatabaseSelectHelper.getUserDetails(userId);
            Employee employee =
                new Employee(user.getId(), user.getName(), user.getAge(), user.getAddress(), true);
          }
          break;

        case "2":
          while (userId == -1) {
            try {
              userId = AccountCreation.createAccount(bufferedReader, Roles.CUSTOMER.name());
            } catch (UserCreationException ex) {
              System.out.println("There was an error creating the user account. Please try again.");
            }
          }
          System.out.println("Your userId is " + userId + ". Please remember it.");
          user = DatabaseSelectHelper.getUserDetails(userId);
          Customer customer =
              new Customer(user.getId(), user.getName(), user.getAge(), user.getAddress());
          break;

        case "3":
          System.out.println("Enter the customer id");
          userId = Integer.parseInt(bufferedReader.readLine());
          if (userId != -1) {
            accId = DatabaseInsertHelper.insertAccount(userId);
          }
          if (accId != -1) {
            System.out.println("Account created successful");
          }
          break;

        case "4":
          while (userId == -1) {
            try {
              userId = AccountCreation.createAccount(bufferedReader, Roles.EMPLOYEE.name());
            } catch (UserCreationException ex) {
              System.out.println("There was an error creating the user account. Please try again.");
            }
          }

          System.out.println("Your userId is " + userId + ". Please remember it.");
          user = DatabaseSelectHelper.getUserDetails(userId);
          Employee employee =
              new Employee(user.getId(), user.getName(), user.getAge(), user.getAddress());
          break;

        case "5":
          int itemId = -1;
          Item item;

          List<Item> items = DatabaseSelectHelper.getAllItems();
          if (items.isEmpty()) {
            System.out.println("Insert a new item first.");
            break;
          }

          for (Item i : items) {
            System.out.println(i.getId() + " - " + i.getName() + " : "
                + DatabaseSelectHelper.getInventoryQuantity(i.getId()));
          }

          valid = false;
          System.out.println("Enter the itemId to restock");
          while (!valid) {
            try {
              itemId = Integer.parseInt(bufferedReader.readLine().trim());
              valid = Validator.validateItemId(itemId);
              if (!valid) {
                System.out.println("Enter a valid itemId");
              }
            } catch (NumberFormatException ex) {
              System.out.println("Enter a valid itemId");
            }
          }

          valid = false;
          System.out.println("Enter the quantity to restock: ");
          while (!valid) {
            try {
              quantity = Integer.parseInt(bufferedReader.readLine().trim());
              valid = Validator.validateRestockQuantity(quantity);
              if (!valid) {
                System.out.println("Enter a valid quantity.");
              }
            } catch (NumberFormatException ex) {
              System.out.println("Enter a valid quantity.");
            }
            item = DatabaseSelectHelper.getItem(itemId);
            employeeInterface.restockInventory(item, quantity);
          }
          break;

        case "6":
          String itemType = "";
          BigDecimal price = BigDecimal.ZERO;

          valid = false;
          System.out.println("Enter name of new item: ");
          while (!valid) {
            itemType = bufferedReader.readLine().trim();
            valid = Validator.validateItemName(itemType);
            if (!valid) {
              System.out.println("Enter a valid item name");
            }
          }

          valid = false;
          System.out.println("Enter the price: ");
          while (!valid) {
            try {
              price = new BigDecimal(bufferedReader.readLine().trim());
              valid = Validator.validatePrice(price);
              if (!valid) {
                System.out.println("Enter a valid price:");
              }
            } catch (NumberFormatException ex) {
              System.out.println("Enter a valid price:");
            }
          }

          valid = false;
          System.out.println("Enter the quantity to restock: ");
          while (!valid) {
            try {
              quantity = Integer.parseInt(bufferedReader.readLine());
              valid = Validator.validateNewItemQuantity(quantity);
              if (!valid) {
                System.out.println("Enter a valid quantity:");
              }
            } catch (NumberFormatException ex) {
              System.out.println("Enter a valid quantity:");
            }
          }

          itemId = DatabaseInsertHelper.insertItem(itemType, price);
          item = DatabaseSelectHelper.getItem(itemId);
          employeeInterface.insertInventory(item, quantity);
          break;

        case "7":
          break;

        default:
          System.out.println("Choose a number from the menu.");
      }
    }
  }
}
