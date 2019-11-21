package com.b07.inventory;

//import com.b07.validation.Validator;

import java.util.HashMap;

public class InventoryImpl implements Inventory {

  private HashMap<Item, Integer> itemMap;
  private int totalItems;
  private int maxItems;

  public InventoryImpl() {
    this.maxItems = 500;
    this.itemMap = new HashMap<>();
    this.totalItems = 0;
  }

  @Override
  public HashMap<Item, Integer> getItemMap() {
    return itemMap;
  }

  @Override
  public void setItemMap(HashMap<Item, Integer> itemMap) {
    this.itemMap = itemMap;
    int totalItems = 0;
    for (int i : itemMap.values()) {
      totalItems += i;
    }
    this.totalItems = totalItems;
  }

  @Override
  public void updateMap(Item item, Integer value) {
    if (itemMap.containsKey(item)) {
      itemMap.put(item, itemMap.get(item) + value);
    } else {
      itemMap.put(item, value);
    }
    totalItems += value;
  }

  @Override
  public int getTotalItems() {
    return totalItems;
  }

  @Override
  public int getMaxItems() {
    return maxItems;
  }

  @Override
  public void setMaxItems(int maxItems) {
    this.maxItems = maxItems;
  }
}
