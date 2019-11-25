package com.b07.store.admin;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.store.LogoutButtonController;

public class AdminUIActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admin);

    ImageView logoutButton = findViewById(R.id.admin_logout);
    logoutButton.setOnClickListener(new LogoutButtonController(this));
  }

  @Override
  public void onBackPressed() {
    return;
  }
}
