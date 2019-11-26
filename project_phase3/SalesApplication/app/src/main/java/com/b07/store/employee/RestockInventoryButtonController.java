package com.b07.store.employee;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.b07.store.EmployeeInterface;

public class RestockInventoryButtonController implements View.OnClickListener {
  private Context appContext;
  private EmployeeInterface employeeInterface;

  public RestockInventoryButtonController(Context context, EmployeeInterface employeeInterface){
    appContext = context;
    this.employeeInterface = employeeInterface;
  }
  @Override
  public void onClick(View v) {
    Intent intent = new Intent(appContext, RestockInventoryActivity.class);
    intent.putExtra("employeeInterface", employeeInterface);
    appContext.startActivity(intent);
  }
}
