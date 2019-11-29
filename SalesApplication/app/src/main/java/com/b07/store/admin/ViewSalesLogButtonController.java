package com.b07.store.admin;

import android.content.Context;
import android.content.Intent;
import android.view.View;


public class ViewSalesLogButtonController implements View.OnClickListener {

  private Context appContext;

  public ViewSalesLogButtonController(Context context) {
    appContext = context;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(appContext, SalesLogUIActivity.class);
    appContext.startActivity(intent);
  }
}
