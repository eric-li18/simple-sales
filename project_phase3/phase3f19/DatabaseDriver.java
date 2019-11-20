package com.b07.database;

import com.b07.exceptions.ConnectionFailedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseDriver {
  /**
   * This will connect to existing database, or create it if it's not there.
   * @return the database connection.
   */
  protected static Connection connectOrCreateDataBase() {
    Connection connection = null;
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection("jdbc:sqlite:inventorymgmt.db");
    
    } catch (Exception e) {
      System.out.println("Something went wrong with your connection! see below details: ");
      e.printStackTrace();
    }
    
    return connection;
  }
  
  /**
   * This will initialize the database, or throw a ConnectionFailedException.
   * @param connection the database you'd like to write the tables to.
   * @return the connection you passed in, to allow you to continue.
   * @throws ConnectionFailedException If the tables couldn't be initialized, throw
   */
  protected static Connection initialize(Connection connection) throws ConnectionFailedException {
    if (!initializeDatabase(connection)) {
      throw new ConnectionFailedException();
    }
    return connection;
  }
  
  protected static Connection reInitialize() throws ConnectionFailedException {
    if (clearDatabase()) {
      Connection connection = connectOrCreateDataBase();
      return initialize(connection);
    } else {   
      throw new ConnectionFailedException();
    }
  }
  
  
  
  protected static Connection updateDb(Connection connection) throws ConnectionFailedException {
    if (!updateDatabase(connection)) {
      throw new ConnectionFailedException();
    }
    return connection;
  }
  
  protected static Connection updateAccountDb(Connection connection) throws 
      ConnectionFailedException {
    if (!updateAccountTable(connection)) {
      throw new ConnectionFailedException();
    } else {
      return connection;
    }
  }
  
  /*
   * BELOW THIS POINT ARE PRIVATE METHODS. 
   * DO NOT TOUCH THESE METHODS OR YOUR DATABASE SETUP MAY NOT MATCH WHAT IS BEING GRADED
   */
  
  private static boolean updateAccountTable(Connection connection) {
    Statement statement = null;
    try {
      statement = connection.createStatement();
      
      String sql = "ALTER TABLE ACCOUNT ADD COLUMN ACTIVE INTEGER;";
      statement.executeUpdate(sql);
      statement.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  
  private static boolean updateDatabase(Connection connection) {
    Statement statement = null;
    try {
      statement = connection.createStatement();
      
      String sql = "CREATE TABLE ACCOUNT "
          + "(ID INTEGER PRIMARY KEY NOT NULL, "
          + "USERID INTEGER NOT NULL, "
          + "FOREIGN KEY(USERID) REFERENCES USER(ID))";
      statement.executeUpdate(sql);
      
      sql = "CREATE TABLE ACCOUNTSUMMARY "
          + "(ACCTID INTEGER NOT NULL, "
          + "ITEMID INTEGER NOT NULL, "
          + "QUANTITY INTEGER NOT NULL, "
          + "FOREIGN KEY(ACCTID) REFERENCES ACCOUNT(ID), "
          + "FOREIGN KEY(ITEMID) REFERENCES ITEMS(ID), "
          + "PRIMARY KEY(ACCTID, ITEMID))";
      statement.executeUpdate(sql);
      statement.close();
      updateAccountTable(connection);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  
  private static boolean initializeDatabase(Connection connection) {
    Statement statement = null;
    
    try {
      statement = connection.createStatement();
      
      String sql = "CREATE TABLE ROLES " 
           + "(ID INTEGER PRIMARY KEY NOT NULL," 
           + "NAME TEXT NOT NULL)";
      statement.executeUpdate(sql);
        
      sql = "CREATE TABLE USERS " 
        + "(ID INTEGER PRIMARY KEY NOT NULL," 
        + "NAME TEXT NOT NULL," 
        + "AGE INTEGER NOT NULL," 
        + "ADDRESS CHAR(100))";
      statement.executeUpdate(sql);
        
      sql = "CREATE TABLE USERROLE "
        + "(USERID INTEGER PRIMARY KEY NOT NULL,"
        + "ROLEID INTEGER NOT NULL,"
        + "FOREIGN KEY(USERID) REFERENCES USERS(ID),"
        + "FOREIGN KEY(ROLEID) REFERENCES ROLES(ID))";
      statement.executeUpdate(sql);
        
      sql = "CREATE TABLE USERPW " 
        + "(USERID INTEGER PRIMARY KEY NOT NULL,"
        + "PASSWORD CHAR(64)," 
        + "FOREIGN KEY(USERID) REFERENCES USER(ID))";
      statement.executeUpdate(sql);
        
      sql = "CREATE TABLE ITEMS "
          + "(ID INTEGER PRIMARY KEY NOT NULL,"
          + "NAME CHAR(64) NOT NULL,"
          + "PRICE TEXT NOT NULL)";
      statement.executeUpdate(sql);
      
      sql = "CREATE TABLE INVENTORY "
          + "(ITEMID INTEGER PRIMARY KEY NOT NULL,"
          + "QUANTITY INTEGER NOT NULL,"
          + "FOREIGN KEY(ITEMID) REFERENCES ITEMS(ID))";
      statement.executeUpdate(sql);
      
      sql = "CREATE TABLE SALES "
          + "(ID INTEGER PRIMARY KEY NOT NULL,"
          + "USERID INTEGER NOT NULL,"
          + "TOTALPRICE TEXT NOT NULL,"
          + "FOREIGN KEY(USERID) REFERENCES USERS(ID))";
      statement.executeUpdate(sql);
      
      sql = "CREATE TABLE ITEMIZEDSALES "
          + "(SALEID INTEGER NOT NULL,"
          + "ITEMID INTEGER NOT NULL,"
          + "QUANTITY INTEGER NOT NULL,"
          + "FOREIGN KEY(SALEID) REFERENCES SALES(ID),"
          + "FOREIGN KEY(ITEMID) REFERENCES ITEMS(ID)"
          + "PRIMARY KEY(SALEID, ITEMID))";
      statement.executeUpdate(sql);
      
      statement.close();
      
      updateDatabase(connection);
      
      return true;
      
    } catch (Exception e) {
      e.printStackTrace();
    }
    return false;
  }
  
  private static boolean clearDatabase() {
    Path path = Paths.get("bank.db");
    try {
      Files.deleteIfExists(path);
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
  
}
