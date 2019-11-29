package com.b07.store.admin;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.b07.store.employee.EmployeeUIActivity;
import com.b07.users.Admin;
import com.b07.users.Employee;

public class EmployeeLoginButtonController implements View.OnClickListener {

  private Context appContext;
  private Admin admin;

  public EmployeeLoginButtonController(Context context, Admin admin) {
    appContext = context;
    this.admin = admin;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(appContext, EmployeeUIActivity.class);
    Employee employee = new Employee(admin.getId(), admin.getName(), admin.getAge(),
        admin.getAddress());
    intent.putExtra("user", employee);
    appContext.startActivity(intent);
  }
}
