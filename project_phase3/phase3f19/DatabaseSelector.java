package com.b07.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSelector {
  
  /**
   * get all the roles.
   * @param connection the connection.
   * @return a ResultSet containing all rows of the roles table.
   * @throws SQLException thrown if an SQLException occurs.
   */
  protected static ResultSet getRoles(Connection connection) throws SQLException {
    Statement statement = connection.createStatement();
    ResultSet results = statement.executeQuery("SELECT * FROM ROLES;");
    return results;
  }
  
  /**
   * get the role with id id.
   * @param id the id of the role
   * @param connection the database connection
   * @return a String containing the role.
   * @throws SQLException thrown when something goes wrong with query.
   */
  protected static String getRole(int id, Connection connection) throws SQLException {
    String sql = "SELECT NAME FROM ROLES WHERE ID = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, id);
    ResultSet results = preparedStatement.executeQuery();
    String role = results.getString("NAME");
    results.close();
    return role;
  }
  
  /**
   * get the role of the given user.
   * @param userId the id of the user.
   * @param connection the connection to the database.
   * @return the roleId for the user.
   * @throws SQLException thrown if something goes wrong with the query.
   */
  protected static int getUserRole(int userId, Connection connection) throws SQLException {
    String sql = "SELECT ROLEID FROM USERROLE WHERE USERID = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, userId);
    ResultSet results = preparedStatement.executeQuery();
    int userRole = results.getInt("ROLEID");
    results.close();
    return userRole;
  }
  
  /**
   * get the role of the given user.
   * @param userId the id of the user.
   * @param connection the connection to the database.
   * @return the roleId for the user.
   * @throws SQLException thrown if something goes wrong with the query.
   */
  protected static ResultSet getUsersByRole(int roleId, Connection connection) throws SQLException {
    String sql = "SELECT USERID FROM USERROLE WHERE ROLEID = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, roleId);
    return  preparedStatement.executeQuery();
  }
  
  /**
   * Return all users from the database.
   * @param connection the connection to the database.
   * @return a results set of all rows in the table.
   * @throws SQLException thrown if there is an issue.
   */
  protected static ResultSet getUsersDetails(Connection connection) throws SQLException {
    String sql = "SELECT * FROM USERS";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    return preparedStatement.executeQuery();
  }
  
  /**
   * find all the details about a given user.
   * @param userId the id of the user.
   * @param connection a connection to the database.
   * @return a result set with the details of the user.
   * @throws SQLException thrown when something goes wrong with query.
   */
  protected static ResultSet getUserDetails(int userId, Connection connection) throws SQLException {
    String sql = "SELECT * FROM USERS WHERE ID = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, userId);
    return preparedStatement.executeQuery();
  }
 
  /**
   * get the hashed version of the password.
   * @param userId the user's id.
   * @param connection the database connection.
   * @return the hashed password to be checked against given password.
   * @throws SQLException if a database issue occurs. 
   */
  protected static String getPassword(int userId, Connection connection) throws SQLException {
    String sql = "SELECT PASSWORD FROM USERPW WHERE USERID = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, userId);
    ResultSet results = preparedStatement.executeQuery();
    String hashPassword = results.getString("PASSWORD");
    results.close();
    return hashPassword;
  }
  
  /**
   * get all rows from the items table.
   * @param connection the connection to the database.
   * @return a result set of all items and associated values.
   * @throws SQLException if anything goes wrong.
   */
  protected static ResultSet getAllItems(Connection connection) throws SQLException {
    String sql = "SELECT * FROM ITEMS;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    return preparedStatement.executeQuery();
  }
  
  /**
   * get a specific item and it's details from the database.
   * @param itemId the item of interest.
   * @param connection the database connection.
   * @return result set containing the row in it. 
   * @throws SQLException if something goes wrong.
   */
  protected static ResultSet getItem(int itemId, Connection connection) throws SQLException {
    String sql = "SELECT * FROM ITEMS WHERE ID = ?;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, itemId);
    return preparedStatement.executeQuery();
  }
  
  /**
   * get all rows from the inventory table.
   * @param connection the database connection.
   * @return result set containing all rows from the table inventory.
   * @throws SQLException if something goes wrong.
   */
  protected static ResultSet getInventory(Connection connection) throws SQLException {
    String sql = "SELECT * FROM INVENTORY;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    return preparedStatement.executeQuery();
  }
  
  /**
   * get the quantity on hand of the given item.
   * @param itemId the id of the give item.
   * @param connection the connection to the database.
   * @return the quantity of the given item available.
   * @throws SQLException if something goes wrong.
   */
  protected static int getInventoryQuantity(int itemId, Connection connection) throws SQLException {
    String sql = "SELECT QUANTITY FROM INVENTORY WHERE ITEMID = ?;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, itemId);
    ResultSet results = preparedStatement.executeQuery();
    int quantity = results.getInt("QUANTITY");
    results.close();
    return quantity;
  }
  
  /**
   * return all sales.
   * @param connection the database connection.
   * @return a result set of all sales.
   * @throws SQLException if something goes wrong.
   */
  protected static ResultSet getSales(Connection connection) throws SQLException {
    String sql = "SELECT * FROM SALES;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    return preparedStatement.executeQuery();
  }
  
  /**
   * get the details of a given sale.
   * @param saleId the id of the given sale.
   * @param connection the database connection.
   * @return result set of the details of a given sale.
   * @throws SQLException if something goes wrong.
   */
  protected static ResultSet getSaleById(int saleId, Connection connection) throws SQLException {
    String sql = "SELECT * FROM SALES WHERE ID = ?;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, saleId);
    return preparedStatement.executeQuery();
  }
  
  /**
   * get all sales for a given user.
   * @param userId the id of the user.
   * @param connection the database connection.
   * @return a result set of all sales made to the given user.
   * @throws SQLException if something goes wrong.
   */
  protected static ResultSet getSalesToUser(int userId, Connection connection) throws SQLException {
    String sql = "SELECT * FROM SALES WHERE USERID = ?;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, userId);
    return preparedStatement.executeQuery();
  }
  
  /**
   * get all itemized sales from the itemizedsales table.
   * @param connection the database connection
   * @return all itemized sales.
   * @throws SQLException if something goes wrong.
   */
  protected static ResultSet getItemizedSales(Connection connection) throws SQLException {
    String sql = "SELECT * FROM ITEMIZEDSALES;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    return preparedStatement.executeQuery();
  }
  
  /**
   * get the items sold in the given sale and the quantity of each.
   * @param salesId the id of the sale of interest.
   * @param connection the database connection.
   * @return result set of all rows for the given sale.
   * @throws SQLException if something goes wrong.
   */
  protected static ResultSet getItemizedSaleById(int salesId, Connection connection) 
      throws SQLException {
    String sql = "SELECT * FROM ITEMIZEDSALES WHERE SALEID = ?;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, salesId);
    return preparedStatement.executeQuery();
  }
  
  /*
   * 
   * PHASE 2 ADDED METHODS START
   * 
   */
  
  /**
   * Get the accounts assigned to a given user.
   * @param userId the id of the user.
   * @param connection the connection to the database.
   * @return a result set containing the id's of the accounts.
   * @throws SQLException if something goes wrong.
   */
  protected static ResultSet getUserAccounts(int userId, Connection connection) 
      throws SQLException {
    String sql = "SELECT ID FROM ACCOUNT WHERE USERID = ?;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, userId);
    return preparedStatement.executeQuery();
  }
  
  /**
   * Get the details of a given account.
   * @param accountId the ID of the account.
   * @param connection the connection to the database. 
   * @return the details associated to the given account.
   * @throws SQLException if something goes wrong.
   */
  protected static ResultSet getAccountDetails(int accountId, Connection connection) 
      throws SQLException {
    String sql = "SELECT * FROM ACCOUNTSUMMARY WHERE ACCTID = ?;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, accountId);
    return preparedStatement.executeQuery();
  }
  
  /*
   * 
   * PHASE 2 ADDED METHODS END
   * 
   */
  
  /*
   * 
   * PHASE 3 ADDED METHODS START
   * 
   */
  
  /**
   * Return a result set containing the active accounts for a given user.
   * @param userId the user.
   * @param connection connection to the database.
   * @return result set of the id's of the given user's active accounts.
   * @throws SQLException if something goes wrong.
   */
  protected static ResultSet getUserActiveAccounts(int userId, Connection connection) 
        throws SQLException {
    String sql = "SELECT ID FROM ACCOUNT WHERE USERID = ? AND ACTIVE = ?;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, userId);
    preparedStatement.setInt(2, 1);
    return preparedStatement.executeQuery();
  }
  
  /**
   * Get a list of the inactive accounts for a given user.
   * @param userId the id of the user.
   * @param connection connection to the database.
   * @return result set containing the ID's of inactive accounts for the user.
   * @throws SQLException if something goes wrong.
   */
  protected static ResultSet getUserInactiveAccounts(int userId, Connection connection) 
        throws SQLException {
    String sql = "SELECT ID FROM ACCOUNT WHERE USERID = ? AND ACTIVE = ?;";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setInt(1, userId);
    preparedStatement.setInt(2, 0);
    return preparedStatement.executeQuery(); 
  }
  
  /*
   * 
   * PHASE 3 ADDED METHODS END
   * 
   */
}