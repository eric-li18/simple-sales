package com.b07.store.admin;

import android.content.Context;
import android.view.View;
import com.b07.store.Sale;
import java.util.List;

public class ReturnButtonController implements View.OnClickListener {
  private Context appContext;
  private List<Sale> salesLog;

  public ReturnButtonController(Context context, List<Sale> salesLog){
    appContext = context;
    this.salesLog = salesLog;
  }

  @Override
  public void onClick(View v) {

  }
}
