package com.b07.store;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.users.Roles;

public class WelcomeActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.welcome);

    TextView name = (TextView) findViewById(R.id.welcome_name);
    TextView role = (TextView) findViewById(R.id.welcome_role);
    TextView userId = (TextView) findViewById(R.id.welcome_user_id);
    TextView anSwitch = (TextView) findViewById(R.id.an_switch);

    Intent intent = getIntent();

    String role_name = intent.getStringExtra("role");
    if (role_name.equals(Roles.CUSTOMER.name())) {
      anSwitch.setText(getResources().getString(R.string.welcome_role_an_switch));
    }

    name.setText(intent.getStringExtra("name"));
    userId.setText(intent.getStringExtra("userId"));
    role.setText(role_name);
  }


  public void continue_button() {
    finish();
  }
}
