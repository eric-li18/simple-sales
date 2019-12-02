package com.b07.store.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
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

    TextView salesView = findViewById(R.id.sales_log_layout);
    SalesLog salesLog = DatabaseSelectHelper.getSales(this);
    List<Sale> sales = salesLog.getLog();
    StringBuilder sales_list = new StringBuilder();
    if (sales != null) {
      for (Sale sale : sales) {
        sales_list.append("Customer: ").append(sale.getUser().getName()).append("\n");
        sales_list.append("Purchase Number: ").append(sale.getId()).append("\n");
        sales_list.append("Total Purchase Price: ").append(sale.getTotalPrice()).append("\n");
        sales_list.append("Itemized Breakdown: ").append("\n");
        HashMap<Item, Integer> itemMap = sale.getItemMap();
        for (Item item : itemMap.keySet()) {
          sales_list.append(item.getName()).append(": ").append(itemMap.get(item)).append("\n");
        }
        sales_list.append("----------------------------------------\n");
      }
    }

    HashMap<Item, Integer> totalItemMap = salesLog.getTotalItemMap();
    for (Item item : totalItemMap.keySet()) {
      sales_list.append(item.getName() + " Sold: " + totalItemMap.get(item) + "\n");
    }


    sales_list.append("TOTAL: ").append(salesLog.getTotalSales().doubleValue());
    salesView.setText(sales_list);

    EditText saleIdInput = findViewById(R.id.sale_id_input);
    saleIdInput.setTransformationMethod(null);

    Button returnButton = findViewById(R.id.return_button);
    returnButton.setOnClickListener(new ReturnButtonController(this, sales));

  }


  @Override
  public void onBackPressed() {
    finish();
  }

}

