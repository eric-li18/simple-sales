package com.b07.store.employee;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.database.DatabaseSelectHelper;
import com.b07.users.Roles;
import com.b07.users.User;
import java.util.List;

public class AddNewAccountActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_account);

    TextView customerView = findViewById(R.id.employee_add_account_users);
    EditText userIdInput = findViewById(R.id.employee_add_account_user_id);
    userIdInput.setTransformationMethod(null);

    int roleId = DatabaseSelectHelper.getRoleIdFromName(Roles.CUSTOMER.name(), this);
    List<Integer> customers = DatabaseSelectHelper.getUsersByRole(roleId, this);

    String customerList = "";
    User user;
    for (int userId : customers) {
      user = DatabaseSelectHelper.getUserDetails(userId, this);
      customerList += user.getId() + " - " + user.getName() + "\n";
    }

    Button addAccountButton = findViewById(R.id.employee_add_account_button);
    addAccountButton.setOnClickListener(new AddAccountButton(this, customers));
    customerView.setText(customerList);
  }
}
