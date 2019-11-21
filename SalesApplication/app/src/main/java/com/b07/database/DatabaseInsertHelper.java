package com.b07.database;

import android.content.Context;

import com.b07.validation.Validator;
import java.math.BigDecimal;

public class DatabaseInsertHelper {

  public static long insertRole(String name, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long roleId = -1;

    if (Validator.validateRoleName(name)) {
      if (Validator.validateRoleUnique(name)) {
        roleId = dbA.insertRole(name);
      } else {
        roleId = DatabaseSelectHelper.getRoleIdFromName(name);
      }
    } else {
      System.out.println("Ensure the method arguments are correct for insertRole.");
    }
    dbA.close();
    return roleId;
  }

  public static long insertNewUser(String name, int age, String address, String password,
      Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long userId = -1;

    if (Validator.validateUserName(name) && Validator.validateAge(age)
        && Validator.validateAddress(address) && Validator.validatePassword(password)) {
      userId = dbA.insertNewUser(name, age, address, password);
    } else {
      System.out.println("Ensure the method arguments are correct for insertNewUser.");
    }
    dbA.close();
    return userId;
  }

  public static long insertUserRole(int userId, int roleId, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long userRoleId = -1;

    userRoleId = dbA.insertUserRole(userId, roleId);
    dbA.close();
    return userRoleId;
  }

  public static long insertItem(String name, BigDecimal price, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long itemId = -1;

    if (Validator.validateItemName(name) && Validator.validatePrice(price)) {
      itemId = dbA.insertItem(name, price);
    } else {
      System.out.println("Ensure the method arguments are correct for insertItem");
    }
    return itemId;
  }

  public static long insertInventory(int itemId, int quantity, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long inventoryId = -1;

    if (Validator.validateNewItemQuantity(quantity) && Validator.validateItemId(itemId)) {
      inventoryId = dbA.insertInventory(itemId, quantity);
    } else {
      System.out.println("Ensure the method arguments are correct for insertInventory.");
    }
    return inventoryId;
  }

  public static long insertSale(int userId, BigDecimal totalPrice, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long saleId = -1;

    if (Validator.validateUserId(userId)) {
      saleId = dbA.insertSale(userId, totalPrice);
    } else {
      System.out.println("Ensure the method arguments are correct for insertSale.");
    }
    return saleId;
  }

  public static long insertItemizedSale(int saleId, int itemId, int quantity, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long itemizedId = -1;

    if (Validator.validateItemId(itemId) && Validator.validateSaleId(saleId)
        && Validator.validateSaleQuantity(quantity)) {
      itemizedId = dbA.insertItemizedSale(saleId, itemId, quantity);
    } else {
      System.out.println("Ensure the method arguments are correct for insertItemizedSale.");
    }
    return itemizedId;
  }

  public static long insertAccount(int userId, boolean active, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long accountId = -1;

    if (Validator.validateUserId(userId)) {
      accountId = dbA.insertAccount(userId, active);
    } else {
      System.out.println("The user id is not valid");
    }
    return accountId;
  }

  public static long insertAccountLine(int accountId, int itemId, int quantity, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long insertId = -1;
    if (Validator.validateItemId(itemId) && Validator.validateSaleQuantity(quantity)) {
      insertId = dbA.insertAccountLine(accountId, itemId, quantity);
    } else {
      System.out.println("Ensure the arguments are correct for insert account line");
    }
    return insertId;
  }
}

