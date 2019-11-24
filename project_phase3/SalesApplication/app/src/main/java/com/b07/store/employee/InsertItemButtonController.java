package com.b07.store.employee;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.b07.store.EmployeeInterface;

public class InsertItemButtonController implements View.OnClickListener {
  private Context appContext;
  private EmployeeInterface employeeInterface;

  public InsertItemButtonController(Context context, EmployeeInterface employeeInterface){
    appContext = context;
    this.employeeInterface = employeeInterface;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(appContext, InsertNewItemActivity.class);
    intent.putExtra("employeeInterface", employeeInterface);
    appContext.startActivity(intent);
  }
}
