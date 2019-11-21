package com.b07.store;

import com.b07.inventory.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SalesLogImpl implements SalesLog {

  private HashMap<Item, Integer> totalItemMap = new HashMap<>();
  private List<Sale> saleLog = new ArrayList<>();

  public List<Sale> getLog() {
    return saleLog;
  }

  public void updateLog(Sale sale) {
    this.saleLog.add(sale);

    HashMap<Item, Integer> itemMap = sale.getItemMap();
    Item itemKey;
    for (Item item : itemMap.keySet()) {
      itemKey = null;
      for (Item item2 : totalItemMap.keySet()) {
        if (item.getId() == item2.getId()) {
          itemKey = item2;
        }
      }
      if (itemKey != null) {
        totalItemMap.put(itemKey, totalItemMap.get(itemKey) + itemMap.get(item));
      } else {
        totalItemMap.put(item, itemMap.get(item));
      }
    }
  }

  public HashMap<Item, Integer> getTotalItemMap() {
    return totalItemMap;
  }

  public BigDecimal getTotalSales() {
    BigDecimal totalSales = BigDecimal.ZERO;
    for (Sale sale : saleLog) {
      totalSales = totalSales.add(sale.getTotalPrice());
    }
    return totalSales;
  }
}
