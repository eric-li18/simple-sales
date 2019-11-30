package com.b07.store.customer;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.b07.store.ShoppingCart;

/**
 * @author Eric
 */
public class CartButtonController implements View.OnClickListener {

  private Context appContext;
  private ShoppingCart cart;

  public CartButtonController(Context context, ShoppingCart cart) {
    appContext = context;
    this.cart = cart;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(appContext, ShoppingCartActivity.class);
    intent.putExtra("cart", cart);
    appContext.startActivity(intent);
  }
}
