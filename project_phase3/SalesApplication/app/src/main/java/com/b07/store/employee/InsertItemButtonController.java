package com.b07.store.employee;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.b07.users.Employee;

public class InsertItemButtonController implements View.OnClickListener {

  private Context appContext;
  private Employee employee;

  public InsertItemButtonController(Context context, Employee employee) {
    appContext = context;
    this.employee = employee;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(appContext, InsertNewItemActivity.class);
    intent.putExtra("user", employee);
    appContext.startActivity(intent);
  }
}
