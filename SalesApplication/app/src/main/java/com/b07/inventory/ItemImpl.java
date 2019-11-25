package com.b07.inventory;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemImpl implements Item, Serializable {

  private int id;
  private String name;
  private BigDecimal price;

  /**
   * Constructor for ItemImpl with default values
   */
  public ItemImpl() {
    this.id = -1;
    this.name = "";
    this.price = BigDecimal.ZERO;
  }

  /**
   * Constructor for ItemImpl with parameters
   *
   * @param id    the itemId
   * @param name  the item name
   * @param price the item price
   */
  public ItemImpl(int id, String name, BigDecimal price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  @Override
  public int getId() {
    return this.id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public BigDecimal getPrice() {
    return this.price;
  }

  @Override
  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
