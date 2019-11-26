package com.b07.store.employee;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.b07.R;
import com.b07.database.DatabaseSelectHelper;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.store.EmployeeInterface;
import com.b07.validation.Validator;
import java.util.HashMap;

public class RestockButtonController implements View.OnClickListener {

  private Context appContext;
  private EmployeeInterface employeeInterface;

  public RestockButtonController(Context context, EmployeeInterface employeeInterface) {
    appContext = context;
    this.employeeInterface = employeeInterface;
  }

  @Override
  public void onClick(View v) {
    EditText itemId = ((RestockInventoryActivity) appContext)
        .findViewById(R.id.employee_restock_item_id);
    EditText itemQuantity = ((RestockInventoryActivity) appContext)
        .findViewById(R.id.employee_restock_item_quantity);
    TextView error = ((RestockInventoryActivity) appContext)
        .findViewById(R.id.employee_restock_item_error);

    TextView inventoryList = ((RestockInventoryActivity) appContext)
        .findViewById(R.id.employee_restock_inventory);

    Integer parsedItemId = -1;
    Integer parsedItemQuantity = -1;

    if (!Validator.validateEmpty(itemId.getText().toString())) {
      parsedItemId = Integer.parseInt(itemId.getText().toString());
    }

    if (!Validator.validateEmpty(itemQuantity.getText().toString())) {
      parsedItemQuantity = Integer.parseInt(itemQuantity.getText().toString());
    }

    if (!Validator.validateItemId(parsedItemId, appContext)) {
      error.setText(R.string.item_id_error);
    } else if (!Validator.validateRestockQuantity(parsedItemQuantity)) {
      error.setText(R.string.item_quantity_error);
    }

    Item itemRestock = DatabaseSelectHelper.getItem(parsedItemId, appContext);
    employeeInterface.restockInventory(itemRestock, parsedItemQuantity, appContext);

    Inventory inventory = DatabaseSelectHelper.getInventory(appContext);
    HashMap<Item, Integer> itemMap = inventory.getItemMap();

    Toast toast = Toast.makeText(appContext, "Restocking item...", Toast.LENGTH_SHORT);
    toast.show();

    String inventoryText = "";
    for (Item item : itemMap.keySet()) {
      inventoryText +=
          item.getId() + " - " + item.getName().replace("_", " ") + ": " + itemMap.get(item) + "\n";
    }
    inventoryList.setText(inventoryText);
  }
}
