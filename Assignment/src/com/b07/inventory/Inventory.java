package com.b07.inventory;

import java.util.HashMap;

/**
 * This is an interface of the inventory and the necessary methods within
 *
 * @author bryanliu
 */
public interface Inventory {

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
   * @param item  the item
   * @param value the amount of the item
   */
  public void updateMap(Item item, Integer value);

  /**
   * Method for returning the total amount of items
   *
   * @ the total amount of items
   */
  public int getTotalItems();

  /**
   * Method for returning the max amount of items
   *
   * @return the max amount of items
   */
  public int getMaxItems();

  /**
   * Method for setting or changing the max amount of items
   *
   * @param max the max amount of items
   */
  public void setMaxItems(int max);
}
