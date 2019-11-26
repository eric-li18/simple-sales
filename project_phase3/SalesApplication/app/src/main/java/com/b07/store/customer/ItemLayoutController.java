package com.b07.store.customer;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.b07.inventory.ItemImpl;
import com.b07.store.ShoppingCart;

public class ItemLayoutController implements View.OnClickListener {

  private Context appContext;
  private ItemImpl item;
  private int inventoryQuantity;
  private String itemName;
  private ShoppingCart cart;

  public ItemLayoutController(Context context, ItemImpl item, int inventoryQuantity,
      String itemName, ShoppingCart cart) {
    appContext = context;
    this.item = item;
    this.inventoryQuantity = inventoryQuantity;
    this.itemName = itemName;
    this.cart = cart;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(appContext, AddItemActivity.class);
    intent.putExtra("item", item);
    intent.putExtra("inventoryQuantity", inventoryQuantity);
    intent.putExtra("itemName", itemName);
    intent.putExtra("cart", cart);
    appContext.startActivity(intent);
  }
}
