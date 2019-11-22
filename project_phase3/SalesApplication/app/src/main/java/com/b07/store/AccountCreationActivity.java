package com.b07.store;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.validation.Validator;


public class AccountCreationActivity extends AppCompatActivity {

  TextView sign_up;
  TextView error;
  EditText name;
  EditText age;
  EditText address;
  EditText password;
  EditText password2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sign_up);
    sign_up = (TextView) findViewById(R.id.sign_up);
    error = (TextView) findViewById(R.id.error);
    name = (EditText) findViewById(R.id.name);
    age = (EditText) findViewById(R.id.age);
    address = (EditText) findViewById(R.id.address);
    password = (EditText) findViewById(R.id.password);
    password2 = (EditText) findViewById(R.id.reenter_password);

    sign_up.setText("Sign up as Admin");
  }

  String TAG = "AccountCreation";


  public void signUp(View view) {
    boolean accountError = false;

    Log.e(TAG, "im here");
    if (!Validator.validateUserName(name.getText().toString().trim())) {
      error.setText(R.string.name_eror);
      accountError = true;
    } else if (Validator.validateEmpty(age.getText().toString()) || !Validator.validateAge(Integer.parseInt(age.getText().toString().trim()))) {
      error.setText(R.string.age_error);
      accountError = true;
    } else if (!Validator.validateAddress(address.getText().toString().trim())) {
      error.setText(R.string.address_error);
      accountError = true;
    } else if (!Validator.validatePassword(password.getText().toString())) {
      error.setText(R.string.password_error);
      accountError = true;
    } else if (!password.getText().toString().equals(password2.getText().toString())) {
      error.setText(R.string.password_mismatch);
      accountError = true;
    }

    if (!accountError) {
      error.setText("yay u created an account!");
    }
  }

}
