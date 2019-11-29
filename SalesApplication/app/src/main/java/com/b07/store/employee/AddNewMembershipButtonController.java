package com.b07.store.employee;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class AddNewMembershipButtonController implements View.OnClickListener {
  private Context appContext;

  public AddNewMembershipButtonController(Context context){
    appContext = context;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(appContext, AddNewMembershipActivity.class);
    appContext.startActivity(intent);
  }
}
