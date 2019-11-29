package com.b07.database;

import android.content.Context;
import com.b07.validation.Validator;
import java.math.BigDecimal;

public class DatabaseInsertHelper {

  public static int insertRole(String name, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long roleId = -1;

    if (Validator.validateRoleName(name)) {
      if (Validator.validateRoleUnique(name, context)) {
        roleId = dbA.insertRole(name);
      } else {
        roleId = DatabaseSelectHelper.getRoleIdFromName(name, context);
      }
    } else {
      System.out.println("Ensure the method arguments are correct for insertRole.");
    }
    dbA.close();
    return Math.toIntExact(roleId);
  }

  public static int insertNewUser(String name, int age, String address, String password,
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
    return Math.toIntExact(userId);
  }

  public static int insertNewUserHashed(String name, int age, String address, String password,
      Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long userId = -1;

    if (Validator.validateUserName(name) && Validator.validateAge(age)
        && Validator.validateAddress(address) && Validator.validatePassword(password)) {
      userId = dbA.insertNewUserHashed(name, age, address, password);
    } else {
      System.out.println("Ensure the method arguments are correct for insertNewUser.");
    }
    dbA.close();
    return Math.toIntExact(userId);
  }

  public static int insertUserRole(int userId, int roleId, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long userRoleId = -1;

    userRoleId = dbA.insertUserRole(userId, roleId);
    dbA.close();
    return Math.toIntExact(userRoleId);
  }

  public static int insertItem(String name, BigDecimal price, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long itemId = -1;

    if (Validator.validateItemName(name) && Validator.validatePrice(price)) {
      itemId = dbA.insertItem(name, price);
    } else {
      System.out.println("Ensure the method arguments are correct for insertItem");
    }
    return Math.toIntExact(itemId);
  }

  public static int insertInventory(int itemId, int quantity, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long inventoryId = -1;

    if (Validator.validateNewItemQuantity(quantity) && Validator.validateItemId(itemId, context)) {
      inventoryId = dbA.insertInventory(itemId, quantity);
    } else {
      System.out.println("Ensure the method arguments are correct for insertInventory.");
    }
    return Math.toIntExact(inventoryId);
  }

  public static int insertSale(int userId, BigDecimal totalPrice, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long saleId = -1;

    if (Validator.validateUserId(userId, context)) {
      saleId = dbA.insertSale(userId, totalPrice);
    } else {
      System.out.println("Ensure the method arguments are correct for insertSale.");
    }
    return Math.toIntExact(saleId);
  }

  public static int insertItemizedSale(int saleId, int itemId, int quantity, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long itemizedId = -1;

    if (Validator.validateItemId(itemId, context) && Validator.validateSaleId(saleId, context)
        && Validator.validateSaleQuantity(quantity)) {
      itemizedId = dbA.insertItemizedSale(saleId, itemId, quantity);
    } else {
      System.out.println("Ensure the method arguments are correct for insertItemizedSale.");
    }
    return Math.toIntExact(itemizedId);
  }

  public static int insertAccount(int userId, boolean active, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long accountId = -1;

    if (Validator.validateUserId(userId, context)) {
      accountId = dbA.insertAccount(userId, active);
    } else {
      System.out.println("The user id is not valid");
    }
    return Math.toIntExact(accountId);
  }

  public static int insertAccountLine(int accountId, int itemId, int quantity, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    long insertId = -1;
    if (Validator.validateItemId(itemId, context) && Validator.validateSaleQuantity(quantity)) {
      insertId = dbA.insertAccountLine(accountId, itemId, quantity);
    } else {
      System.out.println("Ensure the arguments are correct for insert account line");
    }
    return Math.toIntExact(insertId);
  }

  public static int insertMembershipStatus(int userId, boolean status, Context context){
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    //TODO
    return -1;
  }
}

