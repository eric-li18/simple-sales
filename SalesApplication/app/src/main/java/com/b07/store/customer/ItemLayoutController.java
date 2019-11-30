package com.b07.store.customer;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.b07.inventory.ItemImpl;
import com.b07.store.ShoppingCart;

/**
 * @author Eric
 */
public class ItemLayoutController implements View.OnClickListener {

  private ItemImpl item;
  private String itemName;
  private ShoppingCart cart;
  private Activity activity;

  public ItemLayoutController(Activity activity, ItemImpl item, String itemName,
      ShoppingCart cart) {
    this.item = item;
    this.itemName = itemName;
    this.cart = cart;
    this.activity = activity;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(activity, AddItemActivity.class);
    intent.putExtra("item", item);
    intent.putExtra("itemName", itemName);
    intent.putExtra("cart", cart);
    activity.startActivityForResult(intent, 1);
  }
}
