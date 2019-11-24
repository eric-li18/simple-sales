package com.b07.store.employee;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.store.LogoutButtonController;

public class EmployeeUIActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.employee);

    LinearLayout addCustomerButton = findViewById(R.id.employee_add_customer);
    addCustomerButton.setOnClickListener(new AddCustomerButtonController(this));

    LinearLayout addEmployeeButton = findViewById(R.id.employee_add_employee);
    addEmployeeButton.setOnClickListener(new AddEmployeeButtonController(this));

    ImageView logoutButton = findViewById(R.id.employee_logout);
    logoutButton.setOnClickListener(new LogoutButtonController(this));
  }

  @Override
  public void onBackPressed() {
    return;
  }
}
