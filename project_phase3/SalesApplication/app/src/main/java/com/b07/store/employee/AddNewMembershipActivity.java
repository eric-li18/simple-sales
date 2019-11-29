package com.b07.store.employee;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;

public class AddNewMembershipActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_membership);

    LinearLayout addMembershipButton = findViewById(R.id.employee_add_membership);
    addMembershipButton.setOnClickListener(new AddMembershipButtonController(this));
  }
}
