package com.b07.store.customer;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.b07.R;
import java.math.BigDecimal;

public class QuantityButtonController implements View.OnClickListener {

  private Context appContext;
  private int num;
  private int inventoryAmount;
  private BigDecimal price;

  public QuantityButtonController(Context context, int num, int inventoryAmount, BigDecimal price) {
    appContext = context;
    this.num = num;
    this.inventoryAmount = inventoryAmount;
    this.price = price;
  }

  @Override
  public void onClick(View v) {
    TextView quantity = ((Activity) appContext).findViewById(R.id.add_item_quantity);
    TextView totalPrice = ((Activity) appContext).findViewById(R.id.add_item_totalprice);

    int quantityInt = Integer.parseInt(quantity.getText().toString());
    if (num == 1 && quantityInt + 1 <= inventoryAmount) {
      String number = String.valueOf(quantityInt + 1);
      totalPrice.setText(price.multiply(new BigDecimal(quantityInt + 1)).toString());
      quantity.setText(number);
    } else if (num == -1 && quantityInt - 1 >= 0) {
      String number = String.valueOf(quantityInt - 1);
      totalPrice.setText(price.multiply(new BigDecimal(quantityInt - 1)).toString());
      quantity.setText(number);
    }
  }
}
