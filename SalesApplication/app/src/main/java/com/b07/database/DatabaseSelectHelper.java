package com.b07.database;

import android.content.Context;
import android.database.Cursor;
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
import com.b07.validation.Validator;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Eric
 */
public class DatabaseSelectHelper {

  /**
   * Method to return the roleId from role name
   *
   * @param roleName the role name
   * @return the roleId
   */
  public static int getRoleIdFromName(String roleName, Context context) {
    for (int roleId : DatabaseSelectHelper.getRoleIds(context)) {
      if (roleName.contentEquals(DatabaseSelectHelper.getRoleName(roleId, context))) {
        return roleId;
      }
    }
    return -1;
  }

  public static List<Integer> getRoleIds(Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
    Cursor cursor = myDB.getRoles();
    List<Integer> ids = new ArrayList<>();

    while (cursor.moveToNext()) {
      ids.add(cursor.getInt(cursor.getColumnIndex("ID")));
    }
    cursor.close();
    return ids;
  }

  public static String getRoleName(int roleId, Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
    String role = null;
    role = myDB.getRole(roleId);
    return role;
  }

  public static int getUserRoleId(int userId, Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
    int roleId = -1;
    roleId = myDB.getUserRole(userId);
    return roleId;
  }

  public static List<Integer> getUsersByRole(int roleId, Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
    Cursor cursor = null;
    List<Integer> userIds = null;

    cursor = myDB.getUsersByRole(roleId);
    if (cursor != null) {
      userIds = new ArrayList<>();
      while (cursor.moveToNext()) {
        userIds.add(cursor.getInt(cursor.getColumnIndex("USERID")));
     }
      cursor.close();
    }
    return userIds;
  }

  public static List<User> getUsersDetails(Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

    Cursor cursor = null;
    List<User> users = null;

    cursor = myDB.getUsersDetails();
    if (cursor != null) {
      users = new ArrayList<>();
      while (cursor.moveToNext()) {
        int userId = cursor.getInt(cursor.getColumnIndex("ID"));
        String name = cursor.getString(cursor.getColumnIndex("NAME"));
        int age = cursor.getInt(cursor.getColumnIndex("AGE"));
        String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));

        int roleId = DatabaseSelectHelper.getUserRoleId(userId, context);
        String roleName = DatabaseSelectHelper.getRoleName(roleId, context);
        User user = UserFactory.createUser(roleName, userId, name, age, address, context);

        users.add(user);
      }
      cursor.close();
    }
    return users;
  }

  public static User getUserDetails(int userId, Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

    Cursor cursor = null;
    User user = null;
    cursor = myDB.getUserDetails(userId);

    if (cursor.moveToFirst()) {
      int roleId = DatabaseSelectHelper.getUserRoleId(userId, context);
      if (roleId != -1) {
        String roleName = DatabaseSelectHelper.getRoleName(roleId, context);

        int id = cursor.getInt(cursor.getColumnIndex("ID"));
        String name = cursor.getString(cursor.getColumnIndex("NAME"));
        int age = cursor.getInt(cursor.getColumnIndex("AGE"));
        String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));

        user = UserFactory.createUser(roleName, id, name, age, address, context);
      }
      cursor.close();
    }
    return user;
  }

  public static String getPassword(int userId, Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

    String password = null;
    password = myDB.getPassword(userId);
    return password;
  }

  public static List<Item> getAllItems(Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

    Cursor cursor = null;
    List<Item> items = null;

    cursor = myDB.getAllItems();
    if (cursor != null) {
      items = new ArrayList<>();
      while (cursor.moveToNext()) {
        int itemId = cursor.getInt(cursor.getColumnIndex("ID"));
        String name = cursor.getString(cursor.getColumnIndex("NAME"));
        BigDecimal price = new BigDecimal(cursor.getString(cursor.getColumnIndex("PRICE")));
        Item item = new ItemImpl(itemId, name, price);
        items.add(item);
      }
      cursor.close();
    }
    return items;
  }

  public static Item getItem(int itemId, Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

    Item item = null;
    Cursor cursor = null;

    cursor = myDB.getItem(itemId);
    if (cursor != null) {
      item = new ItemImpl();
      item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
      item.setName(cursor.getString(cursor.getColumnIndex("NAME")));
      item.setPrice(new BigDecimal(cursor.getString(cursor.getColumnIndex("PRICE"))));
      cursor.close();
    }
    return item;
  }

  public static Inventory getInventory(Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

    Inventory inventory = null;
    Cursor cursor = null;

    cursor = myDB.getInventory();
    if (cursor != null) {
      inventory = new InventoryImpl();
      while (cursor.moveToNext()) {
        int itemId = cursor.getInt(cursor.getColumnIndex("ITEMID"));
        int quantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
        Item item = DatabaseSelectHelper.getItem(itemId, context);
        inventory.updateMap(item, quantity);
      }
      cursor.close();
    }
    return inventory;
  }

  public static int getInventoryQuantity(int itemId, Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

    int quantity = -1;
    quantity = myDB.getInventoryQuantity(itemId);
    return quantity;
  }

  public static SalesLog getSales(Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

    Cursor cursor = null;
    SalesLog salesLog = null;

    cursor = myDB.getSales();
    if (cursor != null) {
      salesLog = new SalesLogImpl();
      while (cursor.moveToNext()) {
        Sale sale = new SaleImpl();
        int saleId = cursor.getInt(cursor.getColumnIndex("ID"));
        int userId = cursor.getInt(cursor.getColumnIndex("USERID"));
        User user = DatabaseSelectHelper.getUserDetails(userId, context);
        BigDecimal totalPrice = new BigDecimal(
            cursor.getString(cursor.getColumnIndex("TOTALPRICE")));
        Sale itemizedSale = DatabaseSelectHelper.getItemizedSaleById(saleId, context);
        sale.setId(saleId);
        sale.setUser(user);
        sale.setTotalPrice(totalPrice);
        sale.setItemMap(itemizedSale.getItemMap());
        salesLog.updateLog(sale);
      }
      cursor.close();
    }
    return salesLog;
  }

  public static Sale getSaleById(int saleId, Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

    Cursor cursor = null;
    Sale sale = null;

    cursor = myDB.getSaleById(saleId);
    if (cursor != null) {
      User user = DatabaseSelectHelper
          .getUserDetails(cursor.getInt(cursor.getColumnIndex("USERID")), context);
      BigDecimal totalPrice = new BigDecimal(cursor.getString(cursor.getColumnIndex("TOTALPRICE")));
      sale = new SaleImpl();
      sale.setId(saleId);
      sale.setUser(user);
      sale.setTotalPrice(totalPrice);
      cursor.close();
    }
    return sale;
  }

  public static List<Sale> getSalesToUser(int userId, Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

    Cursor cursor = null;
    List<Sale> sales = null;

    cursor = myDB.getSalesToUser(userId);
    if (cursor != null) {
      User user = DatabaseSelectHelper.getUserDetails(userId, context);
      sales = new ArrayList<>();
      while (cursor.moveToNext()) {
        Sale sale = new SaleImpl();
        int id = cursor.getInt(cursor.getColumnIndex("ID"));
        BigDecimal totalPrice = new BigDecimal(
            cursor.getString(cursor.getColumnIndex("TOTALPRICE")));

        sale.setId(id);
        sale.setUser(user);
        sale.setTotalPrice(totalPrice);
        sales.add(sale);
      }
      cursor.close();
    }
    return sales;
  }

  public static Sale getItemizedSaleById(int saleId, Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
    Cursor cursor = null;
    Sale sale = null;

    cursor = myDB.getItemizedSaleById(saleId);
    if (cursor != null) {
      sale = new SaleImpl();
      while (cursor.moveToNext()) {
        int itemId = cursor.getInt(cursor.getColumnIndex("ITEMID"));
        Item item = DatabaseSelectHelper.getItem(itemId, context);
        Integer quantity = cursor.getInt(cursor.getColumnIndex("QUANTITY"));
        sale.updateMap(item, quantity);
      }
      sale.setId(saleId);
      cursor.close();
    }
    return sale;
  }

  public static SalesLog getItemizedSales(Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);

    Cursor cursor = null;
    SalesLog salesLog = null;

    cursor = myDB.getItemizedSales();
    if (cursor != null) {
      salesLog = new SalesLogImpl();
      while (cursor.moveToNext()) {
        int saleId = cursor.getInt(cursor.getColumnIndex("SALEID"));
        Sale sale = getItemizedSaleById(saleId, context);
        salesLog.updateLog(sale);
      }
      cursor.close();
    }
    return salesLog;
  }

  public static int getUserAccounts(int userId, Context context) {
    Cursor cursor = null;
    int accId = -1;

    if (Validator.validateUserId(userId, context)) {
      DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
      cursor = myDB.getUserAccounts(userId);
      if (cursor != null) {
        while (cursor.moveToNext()) {
          accId = cursor.getInt(cursor.getColumnIndex("ID"));
        }
        cursor.close();
      }
    }
    return accId;
  }

  public static HashMap<Item, Integer> getAccountDetails(int accountId, Context context) {
    DatabaseDriverAndroid myDB = new DatabaseDriverAndroid(context);
    Cursor cursor = null;
    HashMap<Item, Integer> items = null;

    cursor = myDB.getAccountDetails(accountId);
    if (cursor != null) {
      items = new HashMap<>();
      while (cursor.moveToNext()) {
        int itemId = cursor.getInt(cursor.getColumnIndex("itemId"));
        int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
        items.put(DatabaseSelectHelper.getItem(itemId, context), quantity);
      }
      cursor.close();
    }
    return items;
  }
}