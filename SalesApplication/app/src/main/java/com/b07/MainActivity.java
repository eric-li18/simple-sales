package com.b07;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.database.DatabaseSelectHelper;
import com.b07.store.AccountCreation;
import com.b07.store.AccountCreationActivity;
import com.b07.store.StoreAuthenticationActivity;
import com.b07.users.Roles;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    startUp();
  }

  private void startUp() {
    int adminId = DatabaseSelectHelper.getRoleIdFromName(Roles.ADMIN.name(), this);
    int employeeId = DatabaseSelectHelper.getRoleIdFromName(Roles.EMPLOYEE.name(), this);
    if (adminId == -1) {
      Intent intent = new Intent(this, AccountCreationActivity.class);
      intent.putExtra("sign_up_display", getResources().getString(R.string.admin_sign_up));
      intent.putExtra("role", Roles.ADMIN.name());
      startActivity(intent);
    }
    else if (employeeId == -1){
      Intent intent = new Intent(this, AccountCreationActivity.class);
      intent.putExtra("sign_up_display", getResources().getString(R.string.employee_sign_up));
      intent.putExtra("role", Roles.EMPLOYEE.name());
      startActivity(intent);
    } else {
      Intent intent = new Intent(this, StoreAuthenticationActivity.class);
      startActivity(intent);
    }
  }
}
