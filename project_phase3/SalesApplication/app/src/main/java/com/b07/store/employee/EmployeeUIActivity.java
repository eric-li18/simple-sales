package com.b07.store.employee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.database.DatabaseSelectHelper;
import com.b07.inventory.Inventory;
import com.b07.store.EmployeeInterface;
import com.b07.store.LogoutButtonController;
import com.b07.users.Employee;
import com.b07.users.User;

public class EmployeeUIActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.employee);

    ImageView logoutButton = findViewById(R.id.employee_logout);
    logoutButton.setOnClickListener(new LogoutButtonController(this));

    Intent intent = getIntent();
    String greeting = "Hi, " + intent.getStringExtra("name");
    TextView greet = findViewById(R.id.employee_greeting);
    greet.setText(greeting);

    Inventory inventory = DatabaseSelectHelper.getInventory(this);
    Integer userId = intent.getIntExtra("userId", -1);
    User user = DatabaseSelectHelper.getUserDetails(userId, this);
    Employee employee = new Employee(user.getId(), user.getName(), user.getAge(),
        user.getAddress(), true);
    EmployeeInterface employeeInterface = new EmployeeInterface(employee, inventory);

    LinearLayout addCustomerButton = findViewById(R.id.employee_add_customer);
    addCustomerButton.setOnClickListener(new AddCustomerButtonController(this));

    LinearLayout addEmployeeButton = findViewById(R.id.employee_add_employee);
    addEmployeeButton.setOnClickListener(new AddEmployeeButtonController(this));

    LinearLayout insertItemButton = findViewById(R.id.employee_insert_item);
    insertItemButton.setOnClickListener(new InsertItemButtonController(this, employeeInterface));



  }

  @Override
  public void onBackPressed() {
    return;
  }
}
