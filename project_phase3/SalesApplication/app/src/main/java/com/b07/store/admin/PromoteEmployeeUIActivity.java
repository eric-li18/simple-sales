package com.b07.store.admin;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.users.Admin;


public class PromoteEmployeeUIActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.promote_employee);

    EditText id = findViewById(R.id.admin_employee_id_input);
    id.setTransformationMethod(null);

    Button promote = findViewById(R.id.promote_button);
    //promote.setOnClickListener(new PromoteButtonController(this, Integer.parseInt(id.getText().toString()),(Admin) getIntent().getSerializableExtra("user")));
    promote.setOnClickListener(
        new PromoteButtonController(this, (Admin) getIntent().getSerializableExtra("user")));

  }

}

