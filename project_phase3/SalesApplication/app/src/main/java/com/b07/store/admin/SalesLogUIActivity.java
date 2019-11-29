package com.b07.store.admin;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.database.DatabaseSelectHelper;
import com.b07.inventory.Item;
import com.b07.store.Sale;
import com.b07.store.SalesLog;
import java.util.HashMap;
import java.util.List;

public class SalesLogUIActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sales_log);

    //TODO back button
    //TODO the sales thing

    //FAKE CART
    /*
    Customer c = new Customer(4, "shawn", 5, "road", this);
    try {
      ShoppingCart sc = new ShoppingCart(c);
      Item rod = new ItemImpl();
      rod.setId(1);
      rod.setName(ItemTypes.FISHING_ROD.name());
      rod.setPrice(BigDecimal.TEN);
      sc.addItem(rod, 2);
      sc.checkOut(this);
    } catch (Exception ignore) {
      System.out.println("EXCEPTION!!");
    }
    */

    ScrollView layout = findViewById(R.id.sales_log_layout);
    SalesLog salesLog = DatabaseSelectHelper.getSales(this);
    List<Sale> sales = salesLog.getLog();
    if (sales != null) {
      for (Sale sale : sales) {
        addSale(layout, sale);
      }
    }
    //TODO returns

  }

  private void addSale(ScrollView sales_log_layout, Sale sale) {

    LinearLayout saleCustomerLayout = findViewById(R.id.sales_log_customer_box);
    TextView saleId = findViewById(R.id.sale_id);
    TextView saleTotal = findViewById(R.id.sales_log_total);
    TextView customerName = findViewById(R.id.sales_log_customer_name);
    saleId.setText(String.valueOf(sale.getId()));
    saleTotal.setText(String.valueOf(sale.getTotalPrice()));
    customerName.setText(sale.getUser().getName());
    saleCustomerLayout.addView(saleId);
    saleCustomerLayout.addView(saleTotal);
    saleCustomerLayout.addView(customerName);

    LinearLayout saleLayout = findViewById(R.id.sales_log_box);
    HashMap<Item, Integer> itemMap = sale.getItemMap();
    for (Item item : itemMap.keySet()) {
      LinearLayout saleItemLayout = findViewById(R.id.sales_log_item_box);
      TextView itemName = findViewById(R.id.sales_log_item);
      TextView itemQuantity = findViewById(R.id.add_item_quantity);
      TextView itemPrice = findViewById(R.id.sales_log_price);
      itemName.setText(item.getName());
      itemQuantity.setText(String.valueOf(itemMap.get(item)));
      itemPrice.setText(String.valueOf(item.getPrice()));
      saleItemLayout.addView(itemName);
      saleItemLayout.addView(itemQuantity);
      saleItemLayout.addView(itemPrice);

      saleLayout.addView(saleItemLayout);
    }

    android.view.ViewGroup.LayoutParams saleParams = saleLayout.getLayoutParams();
    saleParams.height = saleParams.height * itemMap.size();
    saleLayout.setLayoutParams(saleParams);

    android.view.ViewGroup.LayoutParams saleLogParams = sales_log_layout.getLayoutParams();
    saleLogParams.height = saleLogParams.height + (itemMap.size() - 1) * saleParams.height;
    sales_log_layout.addView(saleCustomerLayout);
    sales_log_layout.addView(saleLayout);
    sales_log_layout.setLayoutParams(saleLogParams);
  }

  @Override
  public void onBackPressed() {
    finish();
  }

}

