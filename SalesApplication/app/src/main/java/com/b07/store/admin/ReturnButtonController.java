package com.b07.store.admin;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.b07.R;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.database.DatabaseUpdateHelper;
import com.b07.inventory.Item;
import com.b07.store.Sale;
import com.b07.store.SalesLog;
import com.b07.validation.Validator;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class ReturnButtonController implements View.OnClickListener {

  private Context appContext;
  private List<Sale> salesLog;

  public ReturnButtonController(Context context, List<Sale> salesLog) {
    appContext = context;
    this.salesLog = salesLog;
  }

  @Override
  public void onClick(View v) {
    EditText saleId = ((SalesLogUIActivity) appContext)
        .findViewById(R.id.sale_id_input);

    TextView error = ((SalesLogUIActivity) appContext)
        .findViewById(R.id.sales_id_error);

    Integer parsedSaleId = -1;
    if (!Validator.validateEmpty(saleId.getText().toString().trim())) {
      parsedSaleId = Integer.parseInt(saleId.getText().toString().trim());
    }

    if (Validator.validateSaleId(parsedSaleId, appContext) && Validator
        .validateUniqueReturn(parsedSaleId, appContext)) {
      Sale sale = DatabaseSelectHelper.getSaleById(parsedSaleId, appContext);
      Sale itemizedSale = DatabaseSelectHelper.getItemizedSaleById(parsedSaleId, appContext);

      int userId = sale.getUser().getId();
      BigDecimal totalPrice = sale.getTotalPrice();
      HashMap<Item, Integer> itemMap = itemizedSale.getItemMap();

      int returnSaleId = DatabaseInsertHelper.insertSale(userId, totalPrice.negate(), appContext);

      if (returnSaleId == -1) {
        error.setText(R.string.return_unsucessful);
        return;
      }

      for (Item item : itemMap.keySet()) {
        int itemizedReturnSaleId = DatabaseInsertHelper
            .insertItemizedSale(returnSaleId, item.getId(), -itemMap.get(item), appContext);
        if (itemizedReturnSaleId == -1) {
          error.setText(R.string.return_unsucessful);
          return;
        }

        boolean complete =
            DatabaseUpdateHelper
                .updateInventoryQuantity(itemMap.get(item), item.getId(), appContext);
        DatabaseInsertHelper.insertReturn(parsedSaleId, appContext);

        if (!complete) {
          error.setText(R.string.return_unsucessful);
          return;
        }
      }
      error.setText(R.string.return_successful);
      refreshLog();
    } else {
      error.setText(R.string.sale_id_error);
    }
  }

  private void refreshLog() {
    TextView salesView = ((SalesLogUIActivity) appContext).findViewById(R.id.sales_log_layout);
    SalesLog salesLog = DatabaseSelectHelper.getSales(appContext);
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
        sales_list.append("----------------------------------------");
      }
    }
    salesView.setText(sales_list);
  }
}
