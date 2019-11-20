package com.b07.database;

import com.b07.exceptions.ConnectionFailedException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;


public class DatabaseTesting {
  
  public static void main(String[] args) {
    
    Connection connection = DatabaseDriver.connectOrCreateDataBase();
    try {
      //DatabaseDriver.initialize(connection);
      //connection = DatabaseDriver.updateDb(connection);
      //DatabaseDriver.updateAccountDb(connection);

      int rid = DatabaseInserter.insertRole("ADMIN", connection);
      int rid2 = DatabaseInserter.insertRole("notAdmin", connection);

      int uid = DatabaseInserter.insertNewUser("Mike", 20, "123", "1234321", connection);
      int what = DatabaseInserter.insertUserRole(uid, rid, connection);
      int itemId = DatabaseInserter.insertItem("Hockey Stick", new BigDecimal("5023.32"), 
          connection);
      int inventoryId = DatabaseInserter.insertInventory(itemId, 320, connection);
      int saleId = DatabaseInserter.insertSale(uid, new BigDecimal("20"), connection);
      int itemized = DatabaseInserter.insertItemizedSale(saleId, itemId, 2, connection);
      int acctId = DatabaseInserter.insertAccount(uid, true, connection);
      int acctItem = DatabaseInserter.insertAccountLine(acctId, itemId, 20, connection);
      DatabaseUpdater.updateAccountStatus(acctId, false, connection);
      DatabaseUpdater.updateInventoryQuantity(20, itemId, connection);
      DatabaseUpdater.updateItemName("ASDX", itemId, connection);
      DatabaseUpdater.updateItemPrice(new BigDecimal("343.33"), itemId, connection);
      DatabaseUpdater.updateRoleName("NIMDA", rid, connection);
      DatabaseUpdater.updateUserAddress("fake street", uid, connection);
      DatabaseUpdater.updateUserAge(221, uid, connection);
      DatabaseUpdater.updateUserName("ASDALA", uid, connection);
      DatabaseUpdater.updateUserRole(rid2, uid, connection);
      
      
      s(DatabaseSelector.getAccountDetails(acctId, connection));
      s(DatabaseSelector.getUserAccounts(uid, connection));
      s(DatabaseSelector.getAllItems(connection));
      s(DatabaseSelector.getInventory(connection));
      s(DatabaseSelector.getInventoryQuantity(itemId, connection));
      s(DatabaseSelector.getItem(itemId, connection));
      s(DatabaseSelector.getItemizedSaleById(saleId, connection));
      s(DatabaseSelector.getItemizedSales(connection));
      s(DatabaseSelector.getPassword(uid, connection));
      s(DatabaseSelector.getRole(rid, connection));
      s(DatabaseSelector.getRoles(connection));
      ResultSet rs = DatabaseSelector.getUserActiveAccounts(uid, connection);
      rs.next();
      while (!rs.isAfterLast()) {
        s(rs.getString("ID"));
        s("-000-----------------");
        rs.next();
      }
      rs.close();
      rs = DatabaseSelector.getUserInactiveAccounts(uid, connection);
      rs.next();
      while (!rs.isAfterLast()) {
        s(rs.getString("ID"));
        s("-111-----------------");
        rs.next();
      }
      rs.close();
      s(DatabaseSelector.getSaleById(saleId, connection));
      s(DatabaseSelector.getSales(connection));
      s(DatabaseSelector.getSalesToUser(uid, connection));
      s(DatabaseSelector.getUserDetails(uid, connection));
      s(DatabaseSelector.getUserRole(uid, connection));
      s(DatabaseSelector.getUsersByRole(rid, connection));
      s(DatabaseSelector.getUsersDetails(connection));
           
      System.out.println(what);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } finally {
      try {
        connection.close();
      } catch (Exception e) {
        System.out.println("Already did it!");
      }
    }
  }
  private static void s(Object o) {
    System.out.println(o);
  }
}
