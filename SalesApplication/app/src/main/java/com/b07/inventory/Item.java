package com.b07.inventory;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Interface for an Item
 *
 * @author Eric
 */
public interface Item extends Serializable {

  /**
   * Method for returning the item id
   *
   * @return the item id
   */
  public int getId();

  /**
   * Method for setting or changing the item id
   *
   * @param id the item id
   */
  public void setId(int id);

  /**
   * Method for returning the item name
   *
   * @return the item name
   */
  public String getName();

  /**
   * Method for setting or changing the item name
   *
   * @param name the item name
   */
  public void setName(String name);

  /**
   * Method for returning the item price
   *
   * @return the item price
   */
  public BigDecimal getPrice();

  /**
   * Method for setting or changing the item price
   *
   * @param price the item price
   */
  public void setPrice(BigDecimal price);
}
