package com.b07.store.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.store.LogoutButtonController;
import com.b07.users.Admin;

public class AdminUIActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admin);

    ImageView logoutButton = findViewById(R.id.admin_logout);
    logoutButton.setOnClickListener(new LogoutButtonController(this));

    Intent intent = getIntent();
    Admin admin = (Admin) intent.getSerializableExtra("user");
    String greeting = "Hi, " + admin.getName().split(" ")[0];
    TextView greet = findViewById(R.id.admin_greeting);
    greet.setText(greeting);

    LinearLayout viewSalesLogButton = findViewById(R.id.admin_sales_log);
    viewSalesLogButton.setOnClickListener(new ViewSalesLogButtonController(this));

    LinearLayout viewAccountListButton = findViewById(R.id.admin_account_list);
    viewAccountListButton.setOnClickListener(new ViewAccountListButtonController(this));

    LinearLayout promoteEmployeeButton = findViewById(R.id.admin_promote_employee);
    promoteEmployeeButton.setOnClickListener(new PromoteEmployeeButtonController(this, admin));

    LinearLayout exportDataButton = findViewById(R.id.admin_export_data);
    exportDataButton.setOnClickListener(new ExportDataButtonController(this));

    LinearLayout importDataButton = findViewById(R.id.admin_import_data);
    importDataButton.setOnClickListener(new ImportDataButtonController(this));
  }

  @Override
  public void onBackPressed() {
    finish();
  }
}
