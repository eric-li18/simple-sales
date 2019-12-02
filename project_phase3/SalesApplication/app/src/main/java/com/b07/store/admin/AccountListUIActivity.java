package com.b07.store.admin;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;

public class AccountListUIActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.admin_account_list);

    Button active = findViewById(R.id.admin_active_accounts);
    active.setOnClickListener(new ActiveAccountButtonController(this));
    Button inactive = findViewById(R.id.admin_inactive_accounts);
    inactive.setOnClickListener(new InactiveAccountButtonController(this));
    /*
    TextView accountView = findViewById(R.id.admin_account_list_box);
    int customerRoleId = DatabaseSelectHelper
        .getRoleIdFromName(Roles.CUSTOMER.name(), this);
    List<Integer> customers = DatabaseSelectHelper.getUsersByRole(customerRoleId, this);
    StringBuilder account_list = new StringBuilder();
    if (customers != null) {
      for (int userId : customers) {
        account_list.append("UserId ").append(userId).append(": \n");
        List<Integer> activeAccounts = DatabaseSelectHelper.getActiveAccounts(userId, this);
        List<Integer> inactiveAccounts = DatabaseSelectHelper.getInactiveAccounts(userId, this);
        if (!activeAccounts.isEmpty()) {
          account_list.append("Active accounts: \n");
          for (int activeAccount : activeAccounts) {
            HashMap<Item, Integer> account = DatabaseSelectHelper
                .getAccountDetails(activeAccount, this);
            account_list.append("AccountID ").append(activeAccount).append(": \n");
            for (Map.Entry<Item, Integer> pair : account.entrySet()) {
              account_list.append(pair.getKey().getName()).append(": ");
              account_list.append(pair.getValue()).append("\n");
            }
          }
        }

        if (!inactiveAccounts.isEmpty()) {
          account_list.append("Inactive accounts: \n");
          for (int inactiveAccount : inactiveAccounts) {
            HashMap<Item, Integer> account = DatabaseSelectHelper
                .getAccountDetails(inactiveAccount, this);
            account_list.append("AccountID ").append(inactiveAccount).append(": \n");
            for (Map.Entry<Item, Integer> pair : account.entrySet()) {
              account_list.append(pair.getKey().getName()).append(": ");
              account_list.append(pair.getValue()).append("\n");
            }
          }
        }
        account_list.append("----------------------------------------");
      }
    }
    accountView.setText(account_list);*/

  }

  @Override
  public void onBackPressed() {
    finish();
  }

}

