package com.b07.database.helper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import com.b07.database.DatabaseUpdater;
import com.b07.validation.Validator;

public class DatabaseUpdateHelper extends DatabaseUpdater {


  public static boolean updateRoleName(String name, int id) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    boolean complete = false;

    if (Validator.validateRoleName(name) && Validator.validateRoleId(id)) {
      complete = DatabaseUpdater.updateRoleName(name, id, connection);
      if (!complete) {
        System.out.println("Could not update roleName in the database.");
      }
    }
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException ex) {
      System.out.println("Connection not closed.");
    }
    return complete;
  }

  public static boolean updateUserName(String name, int userId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    boolean complete = false;

    if (Validator.validateUserId(userId) && Validator.validateUserName(name)) {
      complete = DatabaseUpdater.updateUserName(name, userId, connection);
      if (!complete) {
        System.out.println("Could not update userName in the database.");
      }
    }
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException ex) {
      System.out.println("Connection not closed.");
    }
    return complete;
  }

  public static boolean updateUserAge(int age, int userId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    boolean complete = false;

    if (Validator.validateUserId(userId) && Validator.validateAge(age)) {
      complete = DatabaseUpdater.updateUserAge(age, userId, connection);
      if (!complete) {
        System.out.println("Could not update user age in the database.");
      }
    }
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException ex) {
      System.out.println("Connection not closed.");
    }
    return complete;
  }

  public static boolean updateUserAddress(String address, int userId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    boolean complete = false;

    if (Validator.validateUserId(userId) && Validator.validateAddress(address)) {
      complete = DatabaseUpdater.updateUserAddress(address, userId, connection);
      if (!complete) {
        System.out.println("Could not update user address in the database.");
      }
    }
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException ex) {
      System.out.println("Connection not closed.");
    }
    return complete;
  }

  public static boolean updateUserRole(int roleId, int userId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    boolean complete = false;

    if (Validator.validateUserId(userId) && Validator.validateRoleId(roleId)) {
      complete = DatabaseUpdater.updateUserRole(roleId, userId, connection);
      if (!complete) {
        System.out.println("Could not update user role in the database.");
      }
    }
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException ex) {
      System.out.println("Connection not closed.");
    }
    return complete;
  }

  public static boolean updateItemName(String name, int itemId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    boolean complete = false;

    if (Validator.validateItemId(itemId) && Validator.validateItemName(name)) {
      complete = DatabaseUpdater.updateItemName(name, itemId, connection);
      if (!complete) {
        System.out.println("Could not update item name in the database.");
      }
    }
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException ex) {
      System.out.println("Connection not closed.");
    }
    return complete;
  }

  public static boolean updateItemPrice(BigDecimal price, int itemId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    boolean complete = false;

    if (Validator.validateItemId(itemId) && Validator.validatePrice(price)) {
      complete = DatabaseUpdater.updateItemPrice(price, itemId, connection);
      if (!complete) {
        System.out.println("Could not update item price in the database.");
      }
    }
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException ex) {
      System.out.println("Connection not closed.");
    }
    return complete;
  }

  public static boolean updateInventoryQuantity(int quantity, int itemId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    boolean complete = false;
    quantity = quantity + DatabaseSelectHelper.getInventoryQuantity(itemId);

    if (Validator.validateRestockQuantity(quantity) && Validator.validateItemId(itemId)) {
      complete = DatabaseUpdater.updateInventoryQuantity(quantity, itemId, connection);
      if (!complete) {
        System.out.println("Could not update inventory quantity in the database.");
      }
    }
    try {
      if (connection != null) {
        connection.close();
      }
    } catch (SQLException ex) {
      System.out.println("Connection not closed.");
    }
    return complete;
  }
}
