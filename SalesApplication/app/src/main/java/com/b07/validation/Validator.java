package com.b07.validation;

import android.content.Context;
import com.b07.database.DatabaseSelectHelper;
import com.b07.inventory.Item;
import com.b07.inventory.ItemTypes;
import com.b07.inventory.MemberItemTypes;
import com.b07.store.Sale;
import com.b07.users.Roles;
import com.b07.users.User;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Eric
 */
public class Validator {

  /**
   * Method to validate the quantity to restock items in the inventory
   *
   * @param quantity the quantity of items
   * @return true if the quantity is greater than 0, false otherwise
   */
  public static boolean validateRestockQuantity(int quantity) {
    return quantity > 0;
  }

  /**
   * Method to validate the quantity to add a new item to the inventory
   *
   * @param quantity the quantity of items
   * @return true if the quantity is greater than or equal to 0, false otherwise
   */
  public static boolean validateNewItemQuantity(int quantity) {
    return quantity >= 0;
  }

  /**
   * Method to validate the role name
   *
   * @param roleName the role name
   * @return true if the role name is equal to one of the valid roles, false otherwise
   */
  public static boolean validateRoleName(String roleName) {
    if (roleName != null) {
      for (Roles role : Roles.values()) {
        if (roleName.equals(role.name())) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Method to validate if the role name is unique in the Role's table
   *
   * @param roleName the role name
   * @return true if the role name is unique, false otherwise
   */
  public static boolean validateRoleUnique(String roleName, Context context) {
    for (int roleId : DatabaseSelectHelper.getRoleIds(context)) {
      if (roleName.equals(DatabaseSelectHelper.getRoleName(roleId, context))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Method to validate the user's name
   *
   * @param name the user's name
   * @return true if the user's name is not null, false otherwise
   */
  public static boolean validateUserName(String name) {
    if (name != null && !name.equals("")) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to validate the user's age
   *
   * @param age the age
   * @return true if the age is greater than 0, false otherwise
   */
  public static boolean validateAge(int age) {
    return age > 0;
  }

  /**
   * Method to validate the user's address
   *
   * @param address the user's address
   * @return true if the user's address is not null and less than 100 characters, false otherwise
   */
  public static boolean validateAddress(String address) {
    if (address != null && !address.equals("") && address.length() <= 100) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to validate the user's password
   *
   * @param password the password
   * @return true if the password is not null, false otherwise
   */
  public static boolean validatePassword(String password) {
    if (password != null && !password.equals("")) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to validate the userId
   *
   * @param userId the userId
   * @return true if the userId is an id in the User's table, false otherwise
   */
  public static boolean validateUserId(int userId, Context context) {
    User user = DatabaseSelectHelper.getUserDetails(userId, context);
    if (user != null && user.getId() == userId) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to validate the roleId
   *
   * @param roleId the roleId
   * @return true if the roleId is an id in the Role's table, false otherwise
   */
  public static boolean validateRoleId(int roleId, Context context) {
    List<Integer> roleIds = DatabaseSelectHelper.getRoleIds(context);
    return roleIds.contains(roleId);
  }

  /**
   * Method to validate the item name
   *
   * @param itemName the item name
   * @return true if the item name is not null, less than 64 characters and is a valid item type,
   * false otherwise
   */
  public static boolean validateItemName(String itemName) {
    if (itemName != null && itemName.length() < 64) {
      for (ItemTypes i : ItemTypes.values()) {
        if (i.name().equals(itemName)) {
          return true;
        }
      }
      for (MemberItemTypes i : MemberItemTypes.values()) {
        if (i.name().equals(itemName)) {
          return true;
        }
      }
      return false;
    } else {
      return false;
    }
  }

  /**
   * Method to validate the price of an item
   *
   * @param price the item price
   * @return true if the price is greater than 0 and has a scale of 2 decimals, false otherwise
   */
  public static boolean validatePrice(BigDecimal price) {
    if (price.compareTo(BigDecimal.ZERO) == 1 && price.scale() == 2) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to validate the itemId
   *
   * @param itemId the itemId
   * @return true if the itemId is an id in the Items table, false otherwise
   */
  public static boolean validateItemId(int itemId, Context context) {
    Item item = DatabaseSelectHelper.getItem(itemId, context);
    if (item != null && item.getId() == itemId) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to validate the saleId
   *
   * @param saleId the saleId
   * @return true if the saleId is an id in the Sales table, false otherwise
   */
  public static boolean validateSaleId(int saleId, Context context) {
    Sale sale = DatabaseSelectHelper.getSaleById(saleId, context);
    if (sale != null && sale.getId() == saleId) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to validate max items in the inventory
   *
   * @param maxItems max items
   * @return true if the max items is greater than 0, false otherwise
   */
  public static boolean validateMaxItems(int maxItems) {
    return maxItems > 0;
  }

  /**
   * Method to validate that the total items is less than max items in the inventory
   *
   * @param totalItems the total amount of items
   * @param maxItems the max amount of items
   * @return true if totalItems is less than or equal to maxItems, false otherwise
   */
  public static boolean validateTotalLessThanMaxItems(int totalItems, int maxItems) {
    return totalItems <= maxItems;
  }

  public static boolean validateEmpty(String string) {
    return string.trim().equals("");
  }

  public static boolean validateStatus(int status) {
    return status == 1 || status == 0;
  }

  public static boolean validateUniqueReturn(int saleId, Context context) {
    return !DatabaseSelectHelper.getReturns(context).contains(saleId);
  }
}
