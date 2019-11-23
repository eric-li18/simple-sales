package com.b07.store.customer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;

/**
 * @author Eric
 */
public class CustomerUIActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.customer);

    TextView greeting = findViewById(R.id.greeting);
    ImageView logout = findViewById(R.id.customer_logout);

    Intent intent = getIntent();

    greeting.setText(intent.getStringExtra("name"));
    logout.setOnClickListener(new LogoutButtonController(this));

  }
}
