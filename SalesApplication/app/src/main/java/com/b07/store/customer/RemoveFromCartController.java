package com.b07.store.customer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.b07.R;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCart;
import java.math.BigDecimal;

/**
 * @author Eric
 */
public class RemoveFromCartController implements View.OnClickListener {

  private Context appContext;
  private ShoppingCart cart;
  private Item item;
  private int subtotalTextViewId;
  private int itemQuantityId;


  public RemoveFromCartController(Context context, ShoppingCart cart, Item item,
      int subtotalTextViewId, int itemQuantityId) {
    appContext = context;
    this.cart = cart;
    this.item = item;
    this.subtotalTextViewId = subtotalTextViewId;
    this.itemQuantityId = itemQuantityId;
  }

  @Override
  public void onClick(View v) {
    cart.removeItem(item, 1);

    TextView totalPrice = ((ShoppingCartActivity) appContext).findViewById(R.id.cart_totalprice);
    TextView subtotalPrice = ((ShoppingCartActivity) appContext).findViewById(subtotalTextViewId);
    TextView itemQuantity = ((ShoppingCartActivity) appContext).findViewById(itemQuantityId);
    BigDecimal tempPrice = item.getPrice().multiply(new BigDecimal(cart.getItemMap().get(item)));
    BigDecimal total = cart.getTotal().multiply(cart.getTaxRate());
    total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

    itemQuantity.setText(cart.getItemMap().get(item).toString());
    subtotalPrice.setText(tempPrice.toString());
    totalPrice.setText(total.toString());
  }
}
