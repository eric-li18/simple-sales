package com.b07.store.customer;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.b07.store.ShoppingCart;

/**
 * @author Eric
 */
public class CartButtonController implements View.OnClickListener {

  private Activity activity;
  private ShoppingCart cart;

  public CartButtonController(Activity activity, ShoppingCart cart) {
    this.activity = activity;
    this.cart = cart;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(activity, ShoppingCartActivity.class);
    intent.putExtra("cart", cart);
    activity.startActivityForResult(intent, 2);
  }
}
