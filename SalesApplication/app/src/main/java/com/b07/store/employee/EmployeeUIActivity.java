package com.b07.store.employee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.store.LogoutButtonController;
import com.b07.users.Employee;


public class EmployeeUIActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.employee);

    ImageView logoutButton = findViewById(R.id.employee_logout);
    logoutButton.setOnClickListener(new LogoutButtonController(this));

    Intent intent = getIntent();
    Employee employee = (Employee) intent.getSerializableExtra("user");
    String greeting = "Hi, " + employee.getName().split(" ")[0];
    TextView greet = findViewById(R.id.employee_greeting);
    greet.setText(greeting);

    LinearLayout authenticateEmployee = findViewById(R.id.employee_authenticate_employee);
    authenticateEmployee.setOnClickListener(new AuthenticateEmployeeButtonController(this));

    LinearLayout addCustomerButton = findViewById(R.id.employee_add_customer);
    addCustomerButton.setOnClickListener(new AddCustomerButtonController(this));

    LinearLayout addAccountButton = findViewById(R.id.employee_add_account);
    addAccountButton.setOnClickListener(new AddNewAccountButtonController(this));

    LinearLayout addMembershipButton = findViewById(R.id.employee_add_membership);
    addMembershipButton.setOnClickListener(new AddNewMembershipButtonController(this));

    LinearLayout addEmployeeButton = findViewById(R.id.employee_add_employee);
    addEmployeeButton.setOnClickListener(new AddEmployeeButtonController(this));

    LinearLayout insertItemButton = findViewById(R.id.employee_insert_item);
    insertItemButton.setOnClickListener(new InsertItemButtonController(this, employee));

    LinearLayout restockInventoryButton = findViewById(R.id.employee_restock_inventory);
    restockInventoryButton
        .setOnClickListener(new RestockInventoryButtonController(this, employee));


  }

  @Override
  public void onBackPressed() {
    Intent intent = getIntent();
    String backPress = intent.getStringExtra("backPress");
    if (backPress.equals("yes")) {
      super.onBackPressed();
    } else {
      return;
    }
  }
}
