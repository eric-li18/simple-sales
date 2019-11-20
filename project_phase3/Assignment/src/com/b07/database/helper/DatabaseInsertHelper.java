package com.b07.database.helper;

import com.b07.database.DatabaseInserter;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.validation.Validator;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseInsertHelper extends DatabaseInserter {

  public static int insertRole(String name) {
    int roleId = -1;

    if (Validator.validateRoleName(name)) {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

      try {
        if (Validator.validateRoleUnique(name)) {
          roleId = DatabaseInserter.insertRole(name, connection);
        } else {
          roleId = DatabaseSelectHelper.getRoleIdFromName(name);
        }
      } catch (DatabaseInsertException ex) {
        System.out.println("There was a problem inserting role to the database.");
      } finally {
        try {
          if (connection != null) {
            connection.close();
          }
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
      }
    } else {
      System.out.println("Ensure the method arguments are correct for insertRole.");
    }
    return roleId;
  }

  public static int insertNewUser(String name, int age, String address, String password) {
    int userId = -1;

    if (Validator.validateUserName(name) && Validator.validateAge(age)
        && Validator.validateAddress(address) && Validator.validatePassword(password)) {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

      try {
        userId = DatabaseInserter.insertNewUser(name, age, address, password, connection);
      } catch (DatabaseInsertException ex) {
        System.out.println("There was a problem inserting user to the database.");
      } finally {
        try {
          if (connection != null) {
            connection.close();
          }
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
      }
    } else {
      System.out.println("Ensure the method arguments are correct for insertNewUser.");
    }
    return userId;
  }

  public static int insertUserRole(int userId, int roleId) {
    int userRoleId = -1;
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    try {
      userRoleId = DatabaseInserter.insertUserRole(userId, roleId, connection);
    } catch (DatabaseInsertException ex) {
      System.out.println("There was a problem inserting userRole to the database.");
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return userRoleId;
  }

  public static int insertItem(String name, BigDecimal price) {
    int itemId = -1;

    if (Validator.validateItemName(name) && Validator.validatePrice(price)) {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

      try {
        itemId = DatabaseInserter.insertItem(name, price, connection);
      } catch (DatabaseInsertException ex) {
        System.out.println("There was a problem inserting item to the database.");
      } finally {
        try {
          if (connection != null) {
            connection.close();
          }
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
      }
    } else {
      System.out.println("Ensure the method arguments are correct for insertItem");
    }
    return itemId;
  }

  public static int insertInventory(int itemId, int quantity) {
    int inventoryId = -1;

    if (Validator.validateNewItemQuantity(quantity) && Validator.validateItemId(itemId)) {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      try {
        inventoryId = DatabaseInserter.insertInventory(itemId, quantity, connection);
      } catch (DatabaseInsertException ex) {
        System.out.println("There was a problem inserting to inventory in the database.");
      } finally {
        try {
          if (connection != null) {
            connection.close();
          }
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
      }
    } else {
      System.out.println("Ensure the method arguments are correct for insertInventory.");
    }
    return inventoryId;
  }

  public static int insertSale(int userId, BigDecimal totalPrice) {
    int saleId = -1;

    if (Validator.validateUserId(userId)) {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

      try {
        saleId = DatabaseInserter.insertSale(userId, totalPrice, connection);
      } catch (DatabaseInsertException ex) {
        System.out.println("There was a problem inserting the sale to the database.");
      } finally {
        try {
          if (connection != null) {
            connection.close();
          }
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
      }
    } else {
      System.out.println("Ensure the method arguments are correct for insertSale.");
    }
    return saleId;
  }

  public static int insertItemizedSale(int saleId, int itemId, int quantity) {
    int itemizedId = -1;

    if (Validator.validateItemId(itemId) && Validator.validateSaleId(saleId)
        && Validator.validateSaleQuantity(quantity)) {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

      try {
        itemizedId = DatabaseInserter.insertItemizedSale(saleId, itemId, quantity, connection);
      } catch (DatabaseInsertException ex) {
        System.out.println("There was a problem inserting to itemized sale the database.");
      } finally {
        try {
          if (connection != null) {
            connection.close();
          }
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
      }
    } else {
      System.out.println("Ensure the method arguments are correct for insertItemizedSale.");
    }
    return itemizedId;
  }

  public static int insertAccount(int userid) {
    int accountId = -1;

    if (Validator.validateUserId(userid)) {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

      try {
        accountId = DatabaseInserter.insertAccount(userid, connection);
      } catch (DatabaseInsertException e) {
        System.out.println("There was problem inserting a new account");
      } finally {
        try {
          if (connection != null) {
            connection.close();
          }
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
      }
    } else {
      System.out.println("The user id is not valid");
    }
    return accountId;
  }

  public static int insertAccountLine(int accountId, int itemId, int quantity) {
    int insertId = -1;
    if (Validator.validateItemId(itemId) && Validator.validateSaleQuantity(quantity)) {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

      try {
        insertId = DatabaseInserter.insertAccountLine(accountId, itemId, quantity, connection);
      } catch (DatabaseInsertException e) {
        System.out.println("There was problem inserting a new account line");
      } finally {
        try {
          if (connection != null) {
            connection.close();
          }
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
      }
    } else {
      System.out.println("Ensure the arguments are correct for insert account line");
    }
    return insertId;
  }
}

