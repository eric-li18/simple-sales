package com.b07.store.customer;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.b07.R;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCart;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author Eric
 */
public class RemoveFromCartController implements View.OnClickListener {

  private Context appContext;
  private ShoppingCart cart;
  private Item item;
  private int priceId;
  private int quantityId;
  private int emptyItemLayoutId;


  public RemoveFromCartController(Context context, ShoppingCart cart, Item item,
      int priceId, int quantityId, int emptyItemLayoutId) {
    appContext = context;
    this.cart = cart;
    this.item = item;
    this.priceId = priceId;
    this.quantityId = quantityId;
    this.emptyItemLayoutId = emptyItemLayoutId;
  }

  @Override
  public void onClick(View v) {
    HashMap<Item, Integer> itemMap = cart.getItemMap();
    if (itemMap.get(item).compareTo(1) >= 1) {
      cart.removeItem(item, 1);

      TextView totalPrice = ((ShoppingCartActivity) appContext).findViewById(R.id.cart_totalprice);
      TextView subtotalPrice = ((ShoppingCartActivity) appContext).findViewById(priceId);
      TextView itemQuantity = ((ShoppingCartActivity) appContext).findViewById(quantityId);
      BigDecimal tempPrice = item.getPrice().multiply(new BigDecimal(cart.getItemMap().get(item)));
      BigDecimal total = cart.getTotal().multiply(cart.getTaxRate());
      total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);

      itemQuantity.setText(cart.getItemMap().get(item).toString());
      subtotalPrice.setText(tempPrice.toString());
      totalPrice.setText(total.toString());
    } else {
      cart.removeItem(item, 1);

      TextView totalPrice = ((ShoppingCartActivity) appContext).findViewById(R.id.cart_totalprice);
      LinearLayout emptyItemLayout = ((ShoppingCartActivity) appContext)
          .findViewById(emptyItemLayoutId);
      BigDecimal total = cart.getTotal().multiply(cart.getTaxRate());
      total = total.setScale(2, BigDecimal.ROUND_HALF_EVEN);
      totalPrice.setText(total.toString());

      emptyItemLayout.setVisibility(View.GONE);
    }
  }
}
