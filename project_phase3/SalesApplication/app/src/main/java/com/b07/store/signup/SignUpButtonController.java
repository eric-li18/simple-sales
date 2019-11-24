package com.b07.store.signup;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.b07.R;
import com.b07.database.DatabaseInsertHelper;
import com.b07.validation.Validator;

public class SignUpButtonController implements View.OnClickListener {

  private Context appContext;
  private String role;
  private String access;

  public SignUpButtonController(Context context, String role, String access) {
    appContext = context;
    this.role = role;
    this.access = access;
  }

  @Override
  public void onClick(View view) {
    boolean accountError = false;

    EditText name = ((AccountCreationActivity) appContext).findViewById(R.id.name);
    EditText age = ((AccountCreationActivity) appContext).findViewById(R.id.age);
    EditText address = ((AccountCreationActivity) appContext).findViewById(R.id.address);
    EditText password = ((AccountCreationActivity) appContext)
        .findViewById(R.id.password);
    EditText password2 = ((AccountCreationActivity) appContext)
        .findViewById(R.id.reenter_password);

    TextView error = ((AccountCreationActivity)appContext).findViewById(R.id.error);

    String parseName = name.getText().toString().trim();
    Integer parseAge = -1;
    if(!Validator.validateEmpty(age.getText().toString())){
          parseAge = Integer.parseInt(age.getText().toString());
    }
    String parseAddress = address.getText().toString().trim();
    String parsePassword = password.getText().toString();
    String parsePassword2 = password2.getText().toString();

    if (!Validator.validateUserName(parseName)) {
      error.setText(R.string.name_eror);
      accountError = true;
    } else if (!Validator.validateAge(parseAge)) {
      error.setText(R.string.age_error);
      accountError = true;
    } else if (!Validator.validateAddress(parseAddress)) {
      error.setText(R.string.address_error);
      accountError = true;
    } else if (!Validator.validatePassword(parsePassword)) {
      error.setText(R.string.password_error);
      accountError = true;
    } else if (!parsePassword.equals(parsePassword2)) {
      error.setText(R.string.password_mismatch);
      accountError = true;
    }

    if (!accountError) {
      int userId = DatabaseInsertHelper.insertNewUser(parseName,
          Integer.parseInt(age.getText().toString().trim()), parseAddress,
          parsePassword, appContext);
      int roleId = DatabaseInsertHelper.insertRole(role, appContext);
      DatabaseInsertHelper.insertUserRole(userId, roleId, appContext);

      Intent intent = new Intent(appContext, WelcomeActivity.class);
      intent.putExtra("name", parseName);
      intent.putExtra("role", role);
      intent.putExtra("userId", String.valueOf(userId));
      intent.putExtra("access", access);
      appContext.startActivity(intent);
      ((AccountCreationActivity)appContext).finish();
    }
  }
}
