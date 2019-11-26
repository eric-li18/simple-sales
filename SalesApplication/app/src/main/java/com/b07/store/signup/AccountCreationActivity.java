package com.b07.store.signup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.store.AccountCreation;


public class AccountCreationActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.sign_up);

    Intent intent = getIntent();
    String role = intent.getStringExtra("role");
    String access = intent.getStringExtra("access");

    TextView sign_up = findViewById(R.id.sign_up);
    EditText age = findViewById(R.id.age);
    Button signUpButton = findViewById(R.id.sign_up_button);

    sign_up.setText(intent.getStringExtra("sign_up_display"));
    age.setTransformationMethod(null);
    signUpButton.setOnClickListener(new SignUpButtonController(this, role, access));
  }

  @Override
  public void onBackPressed() {
    Intent intent = getIntent();
    if (intent.getStringExtra("backPress").equals("no")){
      return;
    }
    else{
      super.onBackPressed();
    }
  }
}
