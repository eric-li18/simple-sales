package com.b07.database;

import android.content.Context;
import com.b07.validation.Validator;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseUpdateHelper {


  public static boolean updateRoleName(String name, int id, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    boolean complete = false;

    if (Validator.validateRoleName(name) && Validator.validateRoleId(id)) {
      complete = dbA.updateRoleName(name, id);
      if (!complete) {
        System.out.println("Could not update roleName in the database.");
      }
    }
    dbA.close();
    return complete;
  }

  public static boolean updateUserName(String name, int userId, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    boolean complete = false;

    if (Validator.validateUserId(userId) && Validator.validateUserName(name)) {
      complete = dbA.updateUserName(name, userId);
      if (!complete) {
        System.out.println("Could not update userName in the database.");
      }
    }
    dbA.close();
    return complete;
  }

  public static boolean updateUserAge(int age, int userId, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    boolean complete = false;

    if (Validator.validateUserId(userId) && Validator.validateAge(age)) {
      complete = dbA.updateUserAge(age, userId);
      if (!complete) {
        System.out.println("Could not update user age in the database.");
      }
    }
    dbA.close();
    return complete;
  }

  public static boolean updateUserAddress(String address, int userId, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    boolean complete = false;

    if (Validator.validateUserId(userId) && Validator.validateAddress(address)) {
      complete = dbA.updateUserAddress(address, userId);
      if (!complete) {
        System.out.println("Could not update user address in the database.");
      }
    }
    dbA.close();
    return complete;
  }

  public static boolean updateUserRole(int roleId, int userId, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    boolean complete = false;

    if (Validator.validateUserId(userId) && Validator.validateRoleId(roleId)) {
      complete = dbA.updateUserRole(roleId, userId);
      if (!complete) {
        System.out.println("Could not update user role in the database.");
      }
    }
    dbA.close();
    return complete;
  }

  public static boolean updateItemName(String name, int itemId, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    boolean complete = false;

    if (Validator.validateItemId(itemId) && Validator.validateItemName(name)) {
      complete = dbA.updateItemName(name, itemId);
      if (!complete) {
        System.out.println("Could not update item name in the database.");
      }
    }
    dbA.close();
    return complete;
  }

  public static boolean updateItemPrice(BigDecimal price, int itemId, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    boolean complete = false;

    if (Validator.validateItemId(itemId) && Validator.validatePrice(price)) {
      complete = dbA.updateItemPrice(price, itemId);
      if (!complete) {
        System.out.println("Could not update item price in the database.");
      }
    }
    dbA.close();
    return complete;
  }

  public static boolean updateInventoryQuantity(int quantity, int itemId, Context context) {
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    boolean complete = false;
    quantity = quantity + DatabaseSelectHelper.getInventoryQuantity(itemId);

    if (Validator.validateRestockQuantity(quantity) && Validator.validateItemId(itemId)) {
      complete = dbA.updateInventoryQuantity(quantity, itemId);
      if (!complete) {
        System.out.println("Could not update inventory quantity in the database.");
      }
    }
    dbA.close();
    return complete;
  }

  public static boolean updateAccountStatus(int accountId, boolean active, Context context){
    DatabaseDriverAndroid dbA = new DatabaseDriverAndroid(context);
    boolean complete = false;

    /**
     * Needs sanitization
     */
    if(true){
      complete = dbA.updateAccountStatus(accountId, active);
      if (!complete){
        System.out.println("Could not update inventory quantity in the database.");
      }
    }
    dbA.close();
    return complete;
  }
}
