package com.b07.database;

import com.b07.exceptions.DatabaseInsertException;
import com.b07.security.PasswordHelpers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseInserter {
  
  /**
   * Use this to insert new roles into the database.
   * @param role the new role to be added.
   * @param connection the database.
   * @return the id of the role that was inserted.
   * @throws DatabaseInsertException  on failure.
   */
  protected static int insertRole(String role, Connection connection) 
      throws DatabaseInsertException {
    String sql = "INSERT INTO ROLES(NAME) VALUES(?)";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql, 
                                              Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1,role);
      int id = preparedStatement.executeUpdate();
      if (id > 0) {
        ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
        if (uniqueKey.next()) {
          int returnValue = uniqueKey.getInt(1);
          uniqueKey.close();
          preparedStatement.close();
          return returnValue;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    throw new DatabaseInsertException();
  }
  
  /**
   * Use this to insert a new user.
   * @param name the user's name.
   * @param age the user's age.
   * @param address the user's address.
   * @param password the user's password (not hashsed).
   * @param connection the database connection.
   * @return the user id
   * @throws DatabaseInsertException if there is a failure on the insert
   */
  protected static int insertNewUser(String name, int age, String address,
        String password, Connection connection) throws DatabaseInsertException {
    int id = insertUser(name, age, address, connection);
    if (id != -1) {
      insertPassword(password, id, connection);
      return id;
    }
    throw new DatabaseInsertException();
  }
  
  /**
   * Insert a relationship between a user and a role.
   * @param userId the id of the user.
   * @param roleId the role id of the user.
   * @param connection the database connection.
   * @return the unique relationship id.
   * @throws DatabaseInsertException if there is a failure on the insert.
   */
  protected static int insertUserRole(int userId, int roleId, 
      Connection connection) throws DatabaseInsertException {
    String sql = "INSERT INTO USERROLE(USERID, ROLEID) VALUES (?, ?)";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql, 
                                              Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, userId);
      preparedStatement.setInt(2, roleId);
      int id = preparedStatement.executeUpdate();
      if (id > 0) {
        ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
        if (uniqueKey.next()) {
          int returnValue = uniqueKey.getInt(1);
          uniqueKey.close();
          preparedStatement.close();
          return returnValue;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    throw new DatabaseInsertException();
  }
  
  /**
   * insert an item into the database.
   * @param name the name of the item.
   * @param price the price of the item.
   * @param connection the database connection.
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if something goes wrong.
   */
  protected static int insertItem(String name, BigDecimal price, Connection connection) 
      throws DatabaseInsertException {
    String sql = "INSERT INTO ITEMS(NAME, PRICE) VALUES (?, ?)";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql, 
                                              Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, name);
      preparedStatement.setString(2, price.toPlainString());
      int id = preparedStatement.executeUpdate();
      if (id > 0) {
        ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
        if (uniqueKey.next()) {
          int returnValue = uniqueKey.getInt(1);
          uniqueKey.close();
          preparedStatement.close();
          return returnValue;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    throw new DatabaseInsertException();
  }
  
  /**
   * insert inventory into the database.
   * @param itemId the id of the item.
   * @param quantity the quantity of the item.
   * @param connection the database connection.
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if something goes wrong.
   */
  protected static int insertInventory(int itemId, int quantity,
      Connection connection) throws DatabaseInsertException {
    String sql = "INSERT INTO INVENTORY(ITEMID, QUANTITY) VALUES (?,?)";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql,
                                              Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, itemId);
      preparedStatement.setInt(2, quantity);
      
      int id = preparedStatement.executeUpdate();
      if (id > 0) {
        ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
        if (uniqueKey.next()) {
          int returnValue = uniqueKey.getInt(1);
          uniqueKey.close();
          preparedStatement.close();
          return returnValue;
        }
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    throw new DatabaseInsertException();
  }
  
  /**
   * insert a sale into the database.
   * @param userId the id of the user.
   * @param totalPrice the total price of the sale.
   * @param connection the database connection.
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if something goes wrong.
   */
  protected static int insertSale(int userId, BigDecimal totalPrice, Connection connection) 
      throws DatabaseInsertException {
    String sql = "INSERT INTO SALES(USERID, TOTALPRICE) VALUES (?,?)";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql,
                                              Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, userId);
      preparedStatement.setString(2, totalPrice.toPlainString());
      
      int id = preparedStatement.executeUpdate();
      if (id > 0) {
        ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
        if (uniqueKey.next()) {
          int returnValue = uniqueKey.getInt(1);
          uniqueKey.close();
          preparedStatement.close();
          return returnValue;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    throw new DatabaseInsertException();
  }
  
  /**
   * insert an itemized record for a specifc item in a sale.
   * @param saleId the id of the sale.
   * @param itemId the id of the item.
   * @param quantity the number of the item purchased.
   * @param connection the database connection.
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if something goes wrong.
   */
  protected static int insertItemizedSale(int saleId, int itemId, int quantity, 
      Connection connection) throws DatabaseInsertException {
    String sql = "INSERT INTO ITEMIZEDSALES(SALEID, ITEMID, QUANTITY) VALUES (?,?,?)";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql,
                                              Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, saleId);
      preparedStatement.setInt(2, itemId);
      preparedStatement.setInt(3, quantity);
      
      int id = preparedStatement.executeUpdate();
      if (id > 0) {
        ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
        if (uniqueKey.next()) {
          int returnValue = uniqueKey.getInt(1);
          uniqueKey.close();
          preparedStatement.close();
          return returnValue;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    throw new DatabaseInsertException();
  }
  
  /*
   * 
   * PHASE 2 NEW METHODS START
   * 
   */
  
  /**
   * Insert a new account into the database.
   * @param userId the userId for the user of the account.
   * @param connection the connection to the database.
   * @return the id of the account.
   * @throws DatabaseInsertException if something goes wrong.
   */
  protected static int insertAccount(int userId, Connection connection) 
      throws DatabaseInsertException {
    String sql = "INSERT INTO ACCOUNT(USERID) VALUES(?);";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql,
                                              Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, userId);
      
      int id = preparedStatement.executeUpdate();
      if (id > 0) {
        ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
        if (uniqueKey.next()) {
          int returnValue = uniqueKey.getInt(1);
          uniqueKey.close();
          preparedStatement.close();
          return returnValue;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    throw new DatabaseInsertException();
  }
  
  /**
   * insert a single item into a given account for recovery next login.
   * @param accountId the id of the account.
   * @param itemId the item to be inserted.
   * @param quantity the quantity of that item.
   * @param connection connection to the database.
   * @return the id of the inserted record.
   * @throws DatabaseInsertException if something goes wrong.
   */
  protected static int insertAccountLine(int accountId, int itemId, int quantity, 
      Connection connection) throws DatabaseInsertException {
    String sql = "INSERT INTO ACCOUNTSUMMARY(ACCTID, ITEMID, QUANTITY) VALUES(?,?,?);";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql,
                                              Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setInt(1, accountId);
      preparedStatement.setInt(2, itemId);
      preparedStatement.setInt(3, quantity);
      
      int id = preparedStatement.executeUpdate();
      if (id > 0) {
        ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
        if (uniqueKey.next()) {
          int returnValue = uniqueKey.getInt(1);
          uniqueKey.close();
          preparedStatement.close();
          return returnValue;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    throw new DatabaseInsertException();
  }
  
  /*
   * 
   * END PHASE 2 NEW METHODS
   * 
   */
  
  
  private static boolean insertPassword(String password, int userId, Connection connection) {
    String sql = "INSERT INTO USERPW(USERID, PASSWORD) VALUES(?,?);";
    try {
      password = PasswordHelpers.passwordHash(password);
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, userId);
      preparedStatement.setString(2, password);
      preparedStatement.executeUpdate();
      preparedStatement.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  private static int insertUser(String name, int age, String address,
        Connection connection) {
    String sql = "INSERT INTO USERS(NAME, AGE, ADDRESS) VALUES(?,?,?);";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql, 
          Statement.RETURN_GENERATED_KEYS);
      preparedStatement.setString(1, name);
      preparedStatement.setInt(2, age);
      preparedStatement.setString(3, address);
      int id = preparedStatement.executeUpdate();
      if (id > 0) {
        ResultSet uniqueKey = preparedStatement.getGeneratedKeys();
        if (uniqueKey.next()) {
          int returnValue = uniqueKey.getInt(1);
          uniqueKey.close();
          preparedStatement.close();
          return returnValue;
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return -1;
  }
}
