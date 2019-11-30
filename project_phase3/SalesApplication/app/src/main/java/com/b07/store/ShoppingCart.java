package com.b07.store;

import android.content.Context;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.database.DatabaseUpdateHelper;
import com.b07.exceptions.AuthenticationException;
import com.b07.inventory.Item;
import com.b07.users.Customer;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoppingCart implements Serializable {

  private HashMap<Item, Integer> items;
  private Customer customer;
  private BigDecimal total;
  private static final BigDecimal TAXRATE = new BigDecimal(1.13);

  /**
   * Constructor for ShoppingCart
   *
   * @param customer the customer
   */
  public ShoppingCart(Customer customer) throws AuthenticationException {
    if (customer.isAuthenticated()) {
      this.customer = customer;
      items = new HashMap<>();
      total = BigDecimal.ZERO;
    } else {
      throw new AuthenticationException();
    }
  }

  /**
   * Method to add item to the cart
   *
   * @param item the item
   * @param quantity the quantity of the item
   */
  public void addItem(Item item, int quantity) {
    int itemId = item.getId();
    for (Item i : items.keySet()) {
      if (i.getId() == itemId) {
        item = i;
      }
    }

    if (items.containsKey(item) && quantity > 0) {
      items.put(item, items.get(item) + quantity);
    } else {
      items.put(item, quantity);
    }
    total = total.add(new BigDecimal(quantity).multiply(item.getPrice()));
  }

  /**
   * Method to remove item from the cart
   *
   * @param item the item
   * @param quantity the quantity of the item
   */
  public void removeItem(Item item, int quantity) {
    int itemId = item.getId();
    for (Item i : items.keySet()) {
      if (i.getId() == itemId) {
        item = i;
      }
    }
    if (items.containsKey(item) && quantity > 0) {
      int remainingItems = items.get(item) - quantity;
      if (remainingItems == 0) {
        items.remove(item);
        total = total.subtract(new BigDecimal(quantity).multiply(item.getPrice()));
      } else if (remainingItems > 0) {
        items.put(item, remainingItems);
        total = total.subtract(new BigDecimal(quantity).multiply(item.getPrice()));
      } else {
        System.out
            .println("Ensure the quantity to remove is less or equal to the quantity in the cart.");
      }
    } else {
      System.out.println("The item is not in the cart.");
    }
  }

  /**
   * Method to get a list of the items in the cart
   *
   * @return a list of the items
   */
  public List<Item> getItems() {
    List<Item> cartItems = new ArrayList<>(items.keySet());
    return cartItems;
  }

  /**
   * Method for returning the item map
   *
   * @return the item map
   */
  public HashMap<Item, Integer> getItemMap() {
    return items;
  }

  /**
   * Method to get the customer
   *
   * @return the customer
   */
  public Customer getCustomer() {
    return customer;
  }

  /**
   * Method to get the total price of the items in the cart
   *
   * @return the total price
   */
  public BigDecimal getTotal() {
    return total;
  }

  /**
   * Method to get the tax rate
   *
   * @return the tax rate
   */
  public BigDecimal getTaxRate() {
    return TAXRATE;
  }

  /**
   * Method to checkout the items
   *
   * @return true if the operation is successful, false otherwise
   */
  public boolean checkOut(Context context) {
    if (customer != null) {
      BigDecimal totalWithTax = total.multiply(TAXRATE).setScale(2, BigDecimal.ROUND_HALF_EVEN);
      List<Item> itemList = getItems();
      for (Item item : itemList) {
        int quantity = DatabaseSelectHelper.getInventoryQuantity(item.getId(), context);
        int remainingStock = quantity - items.get(item);
        if (remainingStock < 0) {
          return false;
        }
      }
      int saleId = DatabaseInsertHelper.insertSale(customer.getId(), totalWithTax, context);
      if (saleId == -1) {
        return false;
      }

      for (Item item : itemList) {
        int itemizedSaleId =
            DatabaseInsertHelper.insertItemizedSale(saleId, item.getId(), items.get(item), context);
        if (itemizedSaleId == -1) {
          return false;
        }
        boolean complete =
            DatabaseUpdateHelper.updateInventoryQuantity(-items.get(item), item.getId(), context);
        if (!complete) {
          for (Item revert : itemList) {
            DatabaseUpdateHelper.updateInventoryQuantity(+items.get(item), item.getId(), context);
            if (revert.getId() == item.getId()) {
              return false;
            }
          }
          return false;
        }
      }

      clearCart();
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to remove all items from the cart
   */
  public void clearCart() {
    total = BigDecimal.ZERO;
    items.clear();
  }
}
