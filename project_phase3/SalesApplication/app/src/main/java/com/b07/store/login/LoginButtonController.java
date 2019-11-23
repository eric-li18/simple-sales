package com.b07.store.login;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.b07.R;
import com.b07.database.DatabaseSelectHelper;
import com.b07.store.admin.AdminUIActivity;
import com.b07.store.CustomerUIActivity;
import com.b07.store.employee.EmployeeUIActivity;
import com.b07.users.Roles;
import com.b07.users.User;
import com.b07.validation.Validator;

public class LoginButtonController implements View.OnClickListener {

  private Context appContext;

  public LoginButtonController(Context context) {
    appContext = context;
  }

  @Override
  public void onClick(View v) {
    String TAG = "loginActivity";
    EditText userId = ((StoreAuthenticationActivity) appContext).findViewById(R.id.login_userId);
    EditText password = ((StoreAuthenticationActivity) appContext)
        .findViewById(R.id.login_password);
    TextView error = ((StoreAuthenticationActivity) appContext).findViewById(R.id.login_error);

    Integer parsedUserId = -1;
    if (!Validator.validateEmpty(userId.getText().toString())) {
      parsedUserId = Integer.parseInt(userId.getText().toString());
    }
    String parsedPassword = password.getText().toString();

    User user = DatabaseSelectHelper.getUserDetails(parsedUserId, appContext);
    if (user != null && user.authenticate(parsedPassword, appContext)) {
      int roleId = DatabaseSelectHelper.getUserRoleId(parsedUserId, appContext);
      String roleName = DatabaseSelectHelper.getRoleName(roleId, appContext);

      if (roleName.equals(Roles.ADMIN.name())){
        Intent intent = new Intent(appContext, AdminUIActivity.class);
        appContext.startActivity(intent);
      }
      else if(roleName.equals(Roles.EMPLOYEE.name())){
        Intent intent = new Intent(appContext, EmployeeUIActivity.class);
        appContext.startActivity(intent);
      }
      else if(roleName.equals(Roles.CUSTOMER.name())){
        Intent intent = new Intent(appContext, CustomerUIActivity.class);
        appContext.startActivity(intent);
      }
      else{
        Log.e(TAG, "Something went wrong with the authentication.");
      }
    } else {
      error.setText(R.string.login_error);
    }
  }
}
