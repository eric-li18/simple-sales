package com.b07.store.employee;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.b07.R;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseSelectHelper;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.store.EmployeeInterface;
import com.b07.users.Employee;
import com.b07.validation.Validator;
import java.math.BigDecimal;
import java.util.List;

public class InsertItemSubmitButton implements View.OnClickListener {

  private Context appContext;
  private Employee employee;

  public InsertItemSubmitButton(Context context, Employee employee) {
    appContext = context;
    this.employee = employee;
  }

  @Override
  public void onClick(View v) {
    Inventory inventory = DatabaseSelectHelper.getInventory(appContext);
    EmployeeInterface employeeInterface = new EmployeeInterface(employee, inventory);

    TextView error = ((InsertNewItemActivity) appContext)
        .findViewById(R.id.employee_insert_item_error);
    EditText itemName = ((InsertNewItemActivity) appContext)
        .findViewById(R.id.employee_insert_item_name);
    EditText itemPrice = ((InsertNewItemActivity) appContext)
        .findViewById(R.id.employee_insert_item_price);
    EditText itemQuantity = ((InsertNewItemActivity) appContext)
        .findViewById(R.id.employee_insert_item_quantity);

    String parsedItemName = itemName.getText().toString();
    BigDecimal parsedItemPrice = BigDecimal.ZERO;
    if (!Validator.validateEmpty(itemPrice.getText().toString())) {
      parsedItemPrice = new BigDecimal(itemPrice.getText().toString());
    }
    Integer parsedItemQuantity = -1;
    if (!Validator.validateEmpty(itemQuantity.getText().toString())) {
      parsedItemQuantity = Integer.parseInt(itemQuantity.getText().toString());
    }

    if (!Validator.validateItemName(parsedItemName)) {
      error.setText(R.string.item_name_error);
    } else if (!Validator.validatePrice(parsedItemPrice)) {
      error.setText(R.string.item_price_error);
    } else if (!Validator.validateNewItemQuantity(parsedItemQuantity)) {
      error.setText(R.string.item_quantity_error);
    } else {
      List<Item> inventoryItems = DatabaseSelectHelper.getAllItems(appContext);
      for (Item inventoryItem : inventoryItems) {
        if (inventoryItem.getName().equals(parsedItemName)) {
          error.setText(R.string.duplicate_insert_item_error);
          return;
        }
      }

      if (Validator.validateTotalLessThanMaxItems(
          employeeInterface.getInventory().getTotalItems() + parsedItemQuantity,
          employeeInterface.getInventory().getMaxItems())) {
        int itemId = DatabaseInsertHelper.insertItem(parsedItemName, parsedItemPrice, appContext);
        Item item = DatabaseSelectHelper.getItem(itemId, appContext);
        employeeInterface.insertInventory(item, parsedItemQuantity, appContext);
        Toast toast = Toast.makeText(appContext, "Inserting item...", Toast.LENGTH_SHORT);
        toast.show();
        ((InsertNewItemActivity) appContext).finish();
      } else {
        error.setText(R.string.stock_overflow);
      }

    }
  }
}
