package com.b07;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.database.DatabaseSelectHelper;
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
    if (adminId == -1 || employeeId == -1) {
      Intent intent = new Intent(this, AccountCreationActivity.class);
      startActivity(intent);

//      int userId = DatabaseInsertHelper.insertNewUser("Bryan liu", 19, "idk", "abc123", this);
//      int roleId = DatabaseInsertHelper.insertRole(Roles.ADMIN.name(), this);
//      DatabaseInsertHelper.insertUserRole(userId, roleId, this);
//
//      userId = DatabaseInsertHelper.insertNewUser("Eric Li", 19, "idk", "abc123", this);
//      roleId = DatabaseInsertHelper.insertRole(Roles.EMPLOYEE.name(), this);
//      DatabaseInsertHelper.insertUserRole(userId, roleId, this);
    } else {
      Intent intent = new Intent(this, StoreAuthenticationActivity.class);
      startActivity(intent);
    }

  }
}
