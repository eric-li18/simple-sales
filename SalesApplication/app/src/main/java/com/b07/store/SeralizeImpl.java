package com.b07.store;

import android.content.Context;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.exceptions.DatabaseInsertException;
import com.b07.inventory.Inventory;
import com.b07.inventory.InventoryImpl;
import com.b07.inventory.Item;
import com.b07.users.User;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SeralizeImpl implements Serializable, Seralize {

  private HashMap<Integer, String> roles;
  private List<Integer> admin;
  private List<Integer> employee;
  private List<Integer> customer;
  private List<User> users;
  private HashMap<Integer, String> password;
  private List<Item> items;
  private Inventory inventory;
  private SalesLog salesLog;
  private HashMap<Integer, List<Integer>> accounts;
  private List<Integer> activeAccounts;
  private HashMap<Integer, HashMap<Item, Integer>> carts;

  public SeralizeImpl() {
    roles = new HashMap<>();
    admin = new ArrayList<>();
    employee = new ArrayList<>();
    customer = new ArrayList<>();
    users = new ArrayList<>();
    password = new HashMap<>();
    items = new ArrayList<>();
    inventory = new InventoryImpl();
    salesLog = new SalesLogImpl();
    accounts = new HashMap<>();
    carts = new HashMap<>();
  }

  @Override
  public void setRoles(Context appContext) {
    List<Integer> roleIds = DatabaseSelectHelper.getRoleIds(appContext);
    for (int id : roleIds) {
      String name = DatabaseSelectHelper.getRoleName(id, appContext);
      roles.put(id, name);

    }
  }

  @Override
  public void setUserRole(Context appContext) {
    admin = DatabaseSelectHelper.getUsersByRole(1, appContext);
    employee = DatabaseSelectHelper.getUsersByRole(2, appContext);
    customer = DatabaseSelectHelper.getUsersByRole(3, appContext);
  }

  @Override
  public void setUsers(Context appContext) {
    users = DatabaseSelectHelper.getUsersDetails(appContext);
  }

  @Override
  public void setPassword(Context appContext) {
    for (User user : users) {
      int userId = user.getId();
      password.put(userId, DatabaseSelectHelper.getPassword(userId, appContext));
    }
  }

  @Override
  public void setItems(Context appContext) {
    items = DatabaseSelectHelper.getAllItems(appContext);
  }

  @Override
  public void setInventory(Context appContext) {
    inventory = DatabaseSelectHelper.getInventory(appContext);
  }

  @Override
  public void setSalesLog(Context appContext) {
    salesLog = DatabaseSelectHelper.getSales(appContext);
  }

  public void setAccounts(Context appContext) {
    accounts = DatabaseSelectHelper.getAccountMap(appContext);
  }

  public void setActiveAccounts(Context appContext) {
    for (int customerId : customer) {
      activeAccounts.addAll(DatabaseSelectHelper.getActiveAccounts(customerId, appContext));
    }
  }

  @Override
  public void setCarts(Context appContext) {
    for (int customerId : accounts.keySet()) {
      for (int accId : accounts.get(customerId)) {
        HashMap<Item, Integer> items = DatabaseSelectHelper.getAccountDetails(accId, appContext);
        carts.put(accId, items);
      }
    }
  }

  @Override
  public void seralizeDatabase(Context appContext) {
    setRoles(appContext);
    setUserRole(appContext);
    setUsers(appContext);
    setPassword(appContext);
    setItems(appContext);
    setInventory(appContext);
    setSalesLog(appContext);
    setAccounts(appContext);
    setActiveAccounts(appContext);
    setCarts(appContext);
  }

  @Override
  public void deseralizeDatabase(Context appContext) {
    Seralize export = new SeralizeImpl();
    export.seralizeDatabase(appContext);

    try {
      for (int id : roles.keySet()) {
        DatabaseInsertHelper.insertRole(roles.get(id), appContext);
      }
      DatabaseInsertHelper.insertRole("ADMIN", appContext);
      DatabaseInsertHelper.insertRole("EMPLOYEE", appContext);
      DatabaseInsertHelper.insertRole("CUSTOMER", appContext);
      for (User user : users) {
        String name = user.getName();
        int age = user.getAge();
        String address = user.getAddress();
        String userPassword = password.get(user.getId());
        int userId = DatabaseInsertHelper
            .insertNewUserHashed(name, age, address, userPassword, appContext);
        if (admin.contains(user.getId()) && userId == user.getId()) {
          DatabaseInsertHelper.insertUserRole(user.getId(), 1, appContext);
        } else if (employee.contains(user.getId()) && userId == user.getId()) {
          DatabaseInsertHelper.insertUserRole(user.getId(), 2, appContext);
        } else if (customer.contains(user.getId()) && userId == user.getId()) {
          DatabaseInsertHelper.insertUserRole(user.getId(), 3, appContext);
        } else {
          throw new DatabaseInsertException();
        }
      }

      for (Item item : items) {
        String name = item.getName();
        BigDecimal price = item.getPrice();
        int itemId = DatabaseInsertHelper.insertItem(name, price, appContext);
        if (!(itemId == item.getId())) {
          throw new DatabaseInsertException();
        }
      }

      HashMap<Item, Integer> inventoryMap = inventory.getItemMap();
      for (Item item : inventoryMap.keySet()) {
        int quantity = inventoryMap.get(item);
        DatabaseInsertHelper.insertInventory(item.getId(), quantity, appContext);
      }

      for (Sale sale : salesLog.getLog()) {
        int saleId = DatabaseInsertHelper
            .insertSale(sale.getUser().getId(), sale.getTotalPrice(), appContext);
        for (Item item : sale.getItemMap().keySet()) {
          DatabaseInsertHelper
              .insertItemizedSale(saleId, item.getId(), sale.getItemMap().get(item), appContext);
        }
      }

      HashMap<Integer, List<Integer>> newAccountMap = new HashMap<>();
      for (int userId : accounts.keySet()) {
        List<Integer> userAccounts = new ArrayList<>();
        for (int accId : accounts.get(userId)) {
          if (activeAccounts.contains(accId)) {
            userAccounts.add(DatabaseInsertHelper.insertAccount(userId, true, appContext));
          } else {
            userAccounts.add(DatabaseInsertHelper.insertAccount(userId, false, appContext));
          }
        }
        newAccountMap.put(userId, userAccounts);
      }

      for (int accId : carts.keySet()) {
        for (Item item : carts.get(accId).keySet()) {
          int quantity = carts.get(accId).get(item);
          DatabaseInsertHelper.insertAccountLine(accId, item.getId(), quantity, appContext);
        }
      }
    } catch (DatabaseInsertException | NullPointerException e) {
      System.out.println("Import not successful, reverting to the previous version");
      export.deseralizeDatabase(appContext);
    }
  }
}
