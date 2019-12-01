package com.b07.store.customer;

import static android.app.Activity.RESULT_OK;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.b07.R;
import com.b07.database.DatabaseSelectHelper;
import com.b07.inventory.Item;
import com.b07.store.ShoppingCart;
import java.util.HashMap;

public class RestoreButtonController implements View.OnClickListener {

  private Context appContext;
  private ShoppingCart shoppingCart;


  public RestoreButtonController(Context context, ShoppingCart shoppingCart) {
    appContext = context;
    this.shoppingCart = shoppingCart;

  }

  @Override
  public void onClick(View v) {
    TextView error = ((ShoppingCartActivity) appContext).findViewById(R.id.cart_error);

    int userId = shoppingCart.getCustomer().getId();
    int accId = DatabaseSelectHelper.getUserAccounts(userId, appContext);
    if (accId != -1) {
      shoppingCart.clearCart();
      HashMap<Item, Integer> resItems = DatabaseSelectHelper.getAccountDetails(accId, appContext);
      for (Item resItem : resItems.keySet()) {
        int resQuantity = resItems.get(resItem);
        shoppingCart.addItem(resItem, resQuantity);
      }
      if (shoppingCart.getItemMap().isEmpty()) {
        error.setText(R.string.no_save);
        return;
      }

      Toast toast = Toast.makeText(appContext, "Restoring cart...", Toast.LENGTH_SHORT);
      toast.show();

      Intent i = new Intent();
      i.putExtra("cart", shoppingCart);
      ((ShoppingCartActivity) appContext).setResult(RESULT_OK, i);

      ((ShoppingCartActivity) appContext).finish();

    } else {
      error.setText(R.string.account_error);
    }
  }
}
