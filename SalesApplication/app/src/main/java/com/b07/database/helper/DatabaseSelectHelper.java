package com.b07.database.helper;

import com.b07.exceptions.AuthenticationException;
import com.b07.store.ShoppingCart;
import com.b07.validation.Validator;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.b07.database.DatabaseSelector;
import com.b07.inventory.Inventory;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.Item;
import com.b07.inventory.ItemImpl;
import com.b07.store.Sale;
import com.b07.store.SaleImpl;
import com.b07.store.SalesLog;
import com.b07.store.SalesLogImpl;
import com.b07.users.User;
import com.b07.users.UserFactory;

/*
 * TODO: Complete the below methods to be able to get information out of the database. TODO: The
 * given code is there to aide you in building your methods. You don't have TODO: to keep the exact
 * code that is given (for example, DELETE the System.out.println()) TODO: and decide how to handle
 * the possible exceptions
 */
public class DatabaseSelectHelper extends DatabaseSelector {

  /**
   * Method to return the roleId from role name
   *
   * @param roleName the role name
   * @return the roleId
   */
  public static int getRoleIdFromName(String roleName) {
    for (int roleId : DatabaseSelectHelper.getRoleIds()) {
      if (roleName.contentEquals(DatabaseSelectHelper.getRoleName(roleId))) {
        return roleId;
      }
    }
    return -1;
  }

  public static List<Integer> getRoleIds() {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    ResultSet results = null;
    List<Integer> ids = null;

    try {
      results = DatabaseSelector.getRoles(connection);
      ids = new ArrayList<>();
      while (results.next()) {
        ids.add(results.getInt("ID"));
      }
    } catch (SQLException ex) {
      System.out.println("Could not get roleIds from the database.");
    } finally {
      if (results != null) {
        try {
          results.close();
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return ids;
  }

  public static String getRoleName(int roleId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    String role = null;

    try {
      role = DatabaseSelector.getRole(roleId, connection);
    } catch (SQLException ex) {
      System.out.println("Could not get roleName from the database.");
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return role;
  }

  public static int getUserRoleId(int userId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    int roleId = -1;

    try {
      roleId = DatabaseSelector.getUserRole(userId, connection);
    } catch (SQLException ex) {
      System.out.println("Could not get userRoleId from the database.");
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return roleId;
  }

  public static List<Integer> getUsersByRole(int roleId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    ResultSet results = null;
    List<Integer> userIds = null;

    try {
      results = DatabaseSelector.getUsersByRole(roleId, connection);
      userIds = new ArrayList<>();
      while (results.next()) {

        userIds.add(results.getInt("USERID"));
      }
    } catch (SQLException ex) {
      System.out.println("Could not get users by role from the database.");
    } finally {
      try {
        if (results != null) {
          results.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return userIds;
  }

  public static List<User> getUsersDetails() {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    ResultSet results = null;
    List<User> users = null;

    try {
      results = DatabaseSelector.getUsersDetails(connection);
      users = new ArrayList<>();
      while (results.next()) {
        int userId = results.getInt("ID");
        String name = results.getString("NAME");
        int age = results.getInt("AGE");
        String address = results.getString("ADDRESS");

        int roleId = DatabaseSelectHelper.getUserRoleId(userId);
        String roleName = DatabaseSelectHelper.getRoleName(roleId);
        User user = UserFactory.createUser(roleName, userId, name, age, address);

        users.add(user);

      }
    } catch (SQLException ex) {
      System.out.println("Could not get all user's details from the database.");
    } finally {
      if (results != null) {
        try {
          results.close();
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return users;
  }

  public static User getUserDetails(int userId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    ResultSet results = null;
    User user = null;
    int roleId = DatabaseSelectHelper.getUserRoleId(userId);

    if (roleId != -1) {
      String roleName = DatabaseSelectHelper.getRoleName(roleId);
      try {
        results = DatabaseSelector.getUserDetails(userId, connection);
        int id = results.getInt("ID");
        String name = results.getString("NAME");
        int age = results.getInt("AGE");
        String address = results.getString("ADDRESS");

        user = UserFactory.createUser(roleName, id, name, age, address);
      } catch (SQLException ex) {
        System.out.println("Could not get user details from the database.");
      } finally {
        try {
          if (results != null) {
            results.close();
          }
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
        try {
          if (connection != null) {
            connection.close();
          }
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
      }
    }
    return user;
  }

  public static String getPassword(int userId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    String password = null;

    try {
      password = DatabaseSelector.getPassword(userId, connection);
    } catch (SQLException ex) {
      System.out.println("Could not get user's detail from the database.");
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return password;
  }

  public static List<Item> getAllItems() {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    ResultSet results = null;
    List<Item> items = null;

    try {
      results = DatabaseSelector.getAllItems(connection);
      items = new ArrayList<>();
      while (results.next()) {
        int itemId = results.getInt("ID");
        String name = results.getString("NAME");
        BigDecimal price = new BigDecimal(results.getString("PRICE"));
        Item item = new ItemImpl(itemId, name, price);

        items.add(item);
      }
    } catch (SQLException ex) {
      System.out.println("Could not get all items from the database.");
    } finally {
      try {
        if (results != null) {
          results.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return items;
  }

  public static Item getItem(int itemId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    Item item = null;
    ResultSet results = null;

    try {
      results = DatabaseSelector.getItem(itemId, connection);
      item = new ItemImpl();
      item.setId(results.getInt("ID"));
      item.setName(results.getString("NAME"));
      item.setPrice(new BigDecimal(results.getString("PRICE")));
    } catch (SQLException ex) {
      System.out.println("Could not get item from the database.");
    } finally {
      try {
        if (results != null) {
          results.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
      }
    }
    return item;
  }

  public static Inventory getInventory() {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    Inventory inventory = null;
    ResultSet results = null;

    try {
      results = DatabaseSelector.getInventory(connection);
      inventory = new InventoryImpl();
      while (results.next()) {
        int itemId = results.getInt("ITEMID");
        int quantity = results.getInt("QUANTITY");
        Item item = DatabaseSelectHelper.getItem(itemId);
        inventory.updateMap(item, quantity);
      }
    } catch (SQLException ex) {
      System.out.println("Could not get inventory from the database.");
    } finally {
      try {
        if (results != null) {
          results.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return inventory;

  }

  public static int getInventoryQuantity(int itemId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    int quantity = -1;

    try {
      quantity = DatabaseSelector.getInventoryQuantity(itemId, connection);
    } catch (SQLException ex) {
      System.out.println("Could not get inventory quantity from the database");
    } finally {
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return quantity;
  }

  public static SalesLog getSales() {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    ResultSet results = null;
    SalesLog salesLog = null;

    try {
      results = DatabaseSelector.getSales(connection);
      salesLog = new SalesLogImpl();
      while (results.next()) {
        Sale sale = new SaleImpl();
        int saleId = results.getInt("ID");
        int userId = results.getInt("USERID");
        User user = DatabaseSelectHelper.getUserDetails(userId);
        BigDecimal totalPrice = new BigDecimal(results.getString("TOTALPRICE"));
        Sale itemizedSale = DatabaseSelectHelper.getItemizedSaleById(saleId);

        sale.setId(saleId);
        sale.setUser(user);
        sale.setTotalPrice(totalPrice);
        sale.setItemMap(itemizedSale.getItemMap());

        salesLog.updateLog(sale);
      }
    } catch (SQLException ex) {
      System.out.println("Could not get sales from the database.");
    } finally {
      try {
        if (results != null) {
          results.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return salesLog;
  }

  public static Sale getSaleById(int saleId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    ResultSet results = null;
    Sale sale = null;

    try {
      results = DatabaseSelector.getSaleById(saleId, connection);

      User user = DatabaseSelectHelper.getUserDetails(results.getInt("USERID"));
      BigDecimal totalPrice = new BigDecimal(results.getString("TOTALPRICE"));
      sale = new SaleImpl();
      sale.setId(saleId);
      sale.setUser(user);
      sale.setTotalPrice(totalPrice);
    } catch (SQLException ex) {
      System.out.println("Could not get sales by id from the database.");
    } finally {
      try {
        if (results != null) {
          results.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return sale;
  }

  public static List<Sale> getSalesToUser(int userId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    ResultSet results = null;
    List<Sale> sales = null;

    try {
      results = DatabaseSelectHelper.getSalesToUser(userId, connection);
      User user = DatabaseSelectHelper.getUserDetails(userId);
      sales = new ArrayList<>();
      while (results.next()) {
        Sale sale = new SaleImpl();
        int id = results.getInt("ID");
        BigDecimal totalPrice = new BigDecimal(results.getString("TOTALPRICE"));

        sale.setId(id);
        sale.setUser(user);
        sale.setTotalPrice(totalPrice);

        sales.add(sale);
      }
    } catch (SQLException ex) {
      System.out.println("Could not get sales to user from the database.");
    } finally {
      if (results != null) {
        try {
          results.close();
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return sales;
  }

  public static Sale getItemizedSaleById(int saleId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = null;
    Sale sale = null;

    try {
      results = DatabaseSelector.getItemizedSaleById(saleId, connection);

      sale = new SaleImpl();
      while (results.next()) {
        int itemId = results.getInt("ITEMID");
        Item item = DatabaseSelectHelper.getItem(itemId);
        Integer quantity = results.getInt("QUANTITY");
        sale.updateMap(item, quantity);
      }
      sale.setId(saleId);
    } catch (SQLException ex) {
      System.out.println("Could not get itemized sale by id from the database.");
    } finally {
      try {
        if (results != null) {
          results.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return sale;
  }

  public static SalesLog getItemizedSales() {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();

    ResultSet results = null;
    SalesLog salesLog = null;

    try {
      results = DatabaseSelector.getItemizedSales(connection);
      salesLog = new SalesLogImpl();
      while (results.next()) {
        int saleId = results.getInt("SALEID");
        Sale sale = getItemizedSaleById(saleId);
        salesLog.updateLog(sale);
      }
    } catch (SQLException ex) {
      System.out.println("Could not get itemized sales from the database.");
    } finally {
      try {
        if (results != null) {
          results.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return salesLog;
  }

  public static int getUserAccounts(int userId) {
    ResultSet results = null;
    int accId = -1;

    if (Validator.validateUserId(userId)) {
      Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
      try {
        results = DatabaseSelector.getUserAccounts(userId, connection);
        while (results.next()) {
          accId = results.getInt("id");
        }
      } catch (SQLException e) {
        System.out.println("Could not get User Accounts");
      } finally {
        try {
          if (results != null) {
            results.close();
          }
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
        try {
          if (connection != null) {
            connection.close();
          }
        } catch (SQLException ex) {
          System.out.println("Connection not closed.");
        }
      }
    }
    return accId;
  }

  public static HashMap<Item, Integer> getAccountDetails(int accountId) {
    Connection connection = DatabaseDriverHelper.connectOrCreateDataBase();
    ResultSet results = null;
    HashMap<Item, Integer> items = new HashMap<>();

    try {
      results = DatabaseSelector.getAccountDetails(accountId, connection);
      while (results.next()) {
        int itemId = results.getInt("itemId");
        int quantity = results.getInt("quantity");
        items.put(DatabaseSelectHelper.getItem(itemId), quantity);
      }
    } catch (SQLException e) {
      System.out.println("Invalid account id");
    } finally {
      try {
        if (results != null) {
          results.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
      try {
        if (connection != null) {
          connection.close();
        }
      } catch (SQLException ex) {
        System.out.println("Connection not closed.");
      }
    }
    return items;
  }
}