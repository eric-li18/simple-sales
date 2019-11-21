package com.b07.store;

import com.b07.inventory.Item;
import com.b07.users.User;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaleImpl implements Sale {

  private int id;
  private User user;
  private BigDecimal totalPrice;
  private HashMap<Item, Integer> itemMap;

  /**
   * Constructor for a Sale with default values
   */
  public SaleImpl() {
    this.id = -1;
    this.user = null;
    this.totalPrice = BigDecimal.ZERO;
    this.itemMap = new HashMap<>();
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public void setId(int id) {
    this.id = id;
  }

  @Override
  public User getUser() {
    return user;
  }

  @Override
  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public BigDecimal getTotalPrice() {
    return totalPrice;
  }

  @Override
  public void setTotalPrice(BigDecimal price) {
    this.totalPrice = price;
  }

  @Override
  public HashMap<Item, Integer> getItemMap() {
    return itemMap;
  }

  @Override
  public void setItemMap(HashMap<Item, Integer> itemMap) {
    this.itemMap = itemMap;
    List<Item> items = new ArrayList<>(itemMap.keySet());
  }

  @Override
  public void updateMap(Item item, Integer quantity) {
    if (itemMap.containsKey(item)) {
      itemMap.put(item, itemMap.get(item) + quantity);
    } else {
      itemMap.put(item, quantity);
    }
  }
}
