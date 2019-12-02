package com.b07.store.customer;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import com.b07.database.DatabaseSelectHelper;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCart;
import java.util.HashMap;

/**
 * @author Eric
 */
public class CheckOutButtonController implements View.OnClickListener {

  private Context appContext;
  private ShoppingCart cart;

  public CheckOutButtonController(Context context, ShoppingCart cart) {
    appContext = context;
    this.cart = cart;
  }


  @Override
  public void onClick(View v) {
    boolean purchasable = true;
    HashMap<Item, Integer> itemMap = cart.getItemMap();
    if (!itemMap.isEmpty()) {
      Item problemItem = null;
      int dbQuantity = -1;

      for (Item item : itemMap.keySet()) {
        dbQuantity = DatabaseSelectHelper.getInventoryQuantity(item.getId(), appContext);
        if (dbQuantity < itemMap.get(item)) {
          problemItem = item;
          purchasable = false;
          break;
        }
      }

      if (purchasable) {
        if (cart.checkOut(appContext)) {
          Toast.makeText(appContext, "Thank you for shopping with us!", Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(appContext, CustomerUIActivity.class);
          ((ShoppingCartActivity) appContext).finish();
          intent.putExtra("user", cart.getCustomer());
          appContext.startActivity(intent);
        }
      } else {
        String formattedItemName = (problemItem.getName().substring(0, 1).toUpperCase()
            + problemItem
            .getName().substring(1).toLowerCase()).replace("_", " ");
        String msg = "Our inventory has only " + dbQuantity + " of " + formattedItemName;

        Toast.makeText(appContext, msg, Toast.LENGTH_SHORT).show();
      }
    } else {
      Toast.makeText(appContext, "Your cart is empty!", Toast.LENGTH_SHORT).show();
    }
  }
}
