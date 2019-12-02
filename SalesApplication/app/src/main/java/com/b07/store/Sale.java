package com.b07.store;

import com.b07.inventory.Item;
import com.b07.users.User;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Interface for a Sale
 *
 * @author Eric
 */
public interface Sale {

  /**
   * Method for returning sold item id
   *
   * @return the sold item id
   */
  public int getId();

  /**
   * Method for setting or changing the sold item id
   *
   * @param id the sold item id
   */
  public void setId(int id);

  /**
   * Method for getting the user
   *
   * @return the user
   */
  public User getUser();

  /**
   * Method for setting or changing the user
   *
   * @param user the user
   */
  public void setUser(User user);

  /**
   * Method for returning the total price
   *
   * @return the total price
   */
  public BigDecimal getTotalPrice();

  /**
   * Method for setting or changing the total price
   *
   * @param price the total price
   */
  public void setTotalPrice(BigDecimal price);

  /**
   * Method for returning the item map
   *
   * @return the item map
   */
  public HashMap<Item, Integer> getItemMap();

  /**
   * Method for setting or changing the item map
   *
   * @param itemMap the item map
   */
  public void setItemMap(HashMap<Item, Integer> itemMap);

  /**
   * Method for updating the item map
   *
   * @param item the item
   * @param quantity the quantity of the item
   */
  public void updateMap(Item item, Integer quantity);
}
