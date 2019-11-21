package com.b07.store;

import com.b07.database.helper.DatabaseInsertHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.List;
import com.b07.database.helper.DatabaseSelectHelper;
import com.b07.inventory.Item;
import com.b07.validation.Validator;

public class CustomerUI {

  /**
   * Method to give the customer an interface for the shopping cart
   *
   * @param bufferedReader the BufferedReader
   * @param shoppingCart   the shopping cart object for the customer
   */
  public static void shoppingCartInterface(BufferedReader bufferedReader, ShoppingCart shoppingCart)
      throws IOException {

    String userInput = "";

    while (!userInput.equals("8")) {
      List<Item> items;
      HashMap<Item, Integer> itemMap;
      int itemId = -1;
      int quantity = -1;
      int userId = -1;
      int accId = -1;
      Item item;
      boolean valid;

      System.out.println("1. List current items in cart");
      System.out.println("2. Add a quantity of item to the cart");
      System.out.println("3. Check total price of items in the cart");
      System.out.println("4. Remove a quantity of an item from the cart.");
      System.out.println("5. Check out");
      System.out.println("6. Save all items in the cart");
      System.out.println("7. Restore items in the cart");
      System.out.println("8. Exit");

      userInput = bufferedReader.readLine();
      switch (userInput) {
        case "1":
          System.out.println("Items in your cart: ");
          itemMap = shoppingCart.getItemMap();
          for (Item i : itemMap.keySet()) {
            System.out.println(i.getPrice() + " " + i.getName() + " : " + itemMap.get(i));
          }
          break;

        case "2":
          System.out.println("Inventory: ");
          items = DatabaseSelectHelper.getAllItems();
          for (Item i : items) {
            System.out.println(i.getId() + " - " + i.getPrice() + " " + i.getName() + " : "
                + DatabaseSelectHelper.getInventoryQuantity(i.getId()));
          }

          valid = false;
          System.out.println("Enter the itemId to add to your cart.");
          while (!valid) {
            try {
              itemId = Integer.parseInt(bufferedReader.readLine().trim());
              valid = Validator.validateItemId(itemId);
              if (!valid) {
                System.out.println("Enter a valid itemId.");
              }
            } catch (NumberFormatException ex) {
              System.out.println("Enter a valid itemId.");
            }
          }

          valid = false;
          System.out.println("Enter the quantity to add to your cart.");
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
          }

          item = DatabaseSelectHelper.getItem(itemId);
          shoppingCart.addItem(item, quantity);
          break;

        case "3":
          System.out.println(shoppingCart.getTotal());
          break;

        case "4":
          System.out.println("Enter the itemId to remove from your cart.");

          valid = false;
          while (!valid) {
            try {
              itemId = Integer.parseInt(bufferedReader.readLine().trim());
              valid = Validator.validateItemId(itemId);
              if (!valid) {
                System.out.println("Enter a valid itemId.");
              }
            } catch (NumberFormatException ex) {
              System.out.println("Enter a valid itemId.");
            }
          }

          valid = false;
          System.out.println("Enter the quantity to remove from your cart.");
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
          }

          item = DatabaseSelectHelper.getItem(itemId);
          shoppingCart.removeItem(item, quantity);
          break;

        case "5":
          System.out.println("Total price with tax: " + shoppingCart.getTotal()
              .multiply(shoppingCart.getTaxRate()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
          System.out.println("Do you want to continue to checkout?(Y)");
          userInput = bufferedReader.readLine().trim().toUpperCase();
          if (userInput.equals("Y")) {
            shoppingCart.checkOut();
            shoppingCart.clearCart();
          }
          break;

        case "6":
          userId = shoppingCart.getCustomer().getId();
          accId = DatabaseSelectHelper.getUserAccounts(userId);

          if (!DatabaseSelectHelper.getAccountDetails(accId).equals(new HashMap<>())) {
            System.out.println("You have already backed up before");
          } else if (accId != -1) {
            HashMap<Item, Integer> insItems = shoppingCart.getItemMap();
            for (Item insItem : insItems.keySet()) {
              int insId = insItem.getId();
              int insQuantity = insItems.get(insItem);
              DatabaseInsertHelper.insertAccountLine(accId, insId, insQuantity);
            }
          } else {
            System.out.println("You need to link to an account first");
          }
          break;

        case "7":
          userId = shoppingCart.getCustomer().getId();
          accId = DatabaseSelectHelper.getUserAccounts(userId);
          if (accId != -1) {
            HashMap<Item, Integer> resItems = DatabaseSelectHelper.getAccountDetails(accId);
            for (Item resItem : resItems.keySet()) {
              int resQuantity = resItems.get(resItem);
              shoppingCart.addItem(resItem, resQuantity);
            }
          } else {
            System.out.println("You need to link to an account first");
          }

        case "8":
          break;

        default:
          System.out.println("Choose a number from the menu.");
      }
    }
  }
}
