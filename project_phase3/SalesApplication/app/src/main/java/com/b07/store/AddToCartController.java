package com.b07.store;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.b07.R;
import com.b07.database.DatabaseUpdateHelper;
import com.b07.inventory.Item;
import com.b07.store.customer.AddItemActivity;

public class AddToCartController implements View.OnClickListener {

  private Context appContext;
  private Item item;
  private ShoppingCart cart;

  public AddToCartController(Context context, Item item, ShoppingCart cart) {
    appContext = context;
    this.item = item;
    this.cart = cart;
  }

  @Override
  public void onClick(View v) {
    TextView quantityWantedView = ((AddItemActivity) appContext)
        .findViewById(R.id.add_item_quantity);
    int quantityWanted = Integer.parseInt(quantityWantedView.getText().toString());

    if (DatabaseUpdateHelper.updateInventoryQuantity(-quantityWanted, item.getId(), appContext)) {
      cart.addItem(item, quantityWanted);
      Log.e("CLICKED", "INSIDE FORLOOP");
      ((AddItemActivity) appContext).finish();
    } else {
      Toast toast = Toast.makeText(appContext, "Could not process order. Please try again later.",
          Toast.LENGTH_SHORT);
      toast.show();
    }
  }
}