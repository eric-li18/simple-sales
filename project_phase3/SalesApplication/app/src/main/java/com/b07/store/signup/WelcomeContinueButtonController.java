package com.b07.store.signup;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.b07.MainActivity;

public class WelcomeContinueButtonController implements View.OnClickListener {

  private Context appContext;

  public WelcomeContinueButtonController(Context context) {
    appContext = context;
  }

  @Override
  public void onClick(View v) {
    Intent intent = new Intent(appContext, MainActivity.class);
    appContext.startActivity(intent);
  }
}
