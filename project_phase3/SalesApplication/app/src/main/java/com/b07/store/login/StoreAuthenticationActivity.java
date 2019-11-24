package com.b07.store.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;

public class StoreAuthenticationActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.authentication);

    Button loginButton = findViewById(R.id.login_button);
    loginButton.setOnClickListener(new LoginButtonController(this));

    EditText userId = findViewById(R.id.login_userId);
    userId.setTransformationMethod(null);
  }

  @Override
  public void onBackPressed() {
    return;
  }
}
