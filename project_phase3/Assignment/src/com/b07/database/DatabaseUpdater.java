package com.b07.database;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Class to handle all updates to the database for the B07 application.
 * @author Joe
 *
 */
public class DatabaseUpdater {
  
  /**
   * Update the role name of a given role in the role table.
   * @param name the new name of the role.
   * @param id the current ID of the role.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  protected static boolean updateRoleName(String name, int id, Connection connection) {
    String sql = "UPDATE ROLES SET NAME = ? WHERE ID = ?;";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, name);
      preparedStatement.setInt(2, id);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      return true;
        
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  /**
   * Use this to update the user's name.
   * @param name the new name
   * @param id the current id
   * @param connection the database
   * @return true if it works, false otherwise.
   */
  protected static boolean updateUserName(String name, int id, Connection connection) {
    String sql = "UPDATE USERS SET NAME = ? WHERE ID = ?;";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, name);
      preparedStatement.setInt(2, id);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  /**
   * Use this to update the user's age.
   * @param age the new age.
   * @param id the current id
   * @param connection the connection.
   * @return true if it succeeds, false otherwise.
   */
  protected static boolean updateUserAge(int age, int id, Connection connection) {
    String sql = "UPDATE USERS SET AGE = ? WHERE ID = ?;";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, age);
      preparedStatement.setInt(2, id);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  /**
   * Use this to update user's address.
   * @param address the new address.
   * @param id the current id.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  protected static boolean updateUserAddress(String address, int id, Connection connection) {
    String sql = "UPDATE USERS SET ADDRESS = ? WHERE ID = ?;";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, address);
      preparedStatement.setInt(2, id);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * update the role of the user.
   * @param roleId the new role.
   * @param id the current id.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  protected static boolean updateUserRole(int roleId, int userId, Connection connection) {
    String sql = "UPDATE USERROLE SET ROLEID = ? WHERE USERID = ?;";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, roleId);
      preparedStatement.setInt(2, userId);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  /**
   * Update the name of an item currently in the database.
   * @param name the new name.
   * @param id the id of the current item.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  protected static boolean updateItemName(String name, int id, Connection connection) {
    String sql = "UPDATE ITEMS SET NAME = ? WHERE ID = ?;";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, name);
      preparedStatement.setInt(2, id);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  /**
   * update the price of an item in the database.
   * @param price the new price for the item.
   * @param id the id of the item.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  protected static boolean updateItemPrice(BigDecimal price, int id, Connection connection) {
    String sql = "UPDATE ITEMS SET PRICE = ? WHERE ID = ?;";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setString(1, price.toPlainString());
      preparedStatement.setInt(2, id);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  /**
   * update the quantity available in inventory for a given item.
   * @param quantity the new quantity.
   * @param itemId the item to be updated.
   * @param connection the database connection.
   * @return true if successful, false otherwise.
   */
  protected static boolean updateInventoryQuantity(int quantity, int itemId, 
      Connection connection) {
    String sql = "UPDATE INVENTORY SET QUANTITY = ? WHERE ITEMID = ?;";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1,quantity);
      preparedStatement.setInt(2, itemId);
      preparedStatement.executeUpdate();
      preparedStatement.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
}