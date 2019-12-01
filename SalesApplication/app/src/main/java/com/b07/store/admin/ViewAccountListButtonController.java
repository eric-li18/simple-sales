package com.b07.store.admin;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class ViewAccountListButtonController implements View.OnClickListener {

  private Context appContext;

  public ViewAccountListButtonController(Context context) {
    appContext = context;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(appContext, AccountListUIActivity.class);
    appContext.startActivity(intent);
  }
}
