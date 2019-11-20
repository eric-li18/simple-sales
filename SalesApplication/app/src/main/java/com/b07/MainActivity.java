package com.b07.store;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.R;
import com.b07.users.AccountCreationActivity;
import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String TAG = "MainActivity";
    Connection connection = DatabaseDriverExtender.connectOrCreateDataBase();
    if (connection == null) {
      Log.e(TAG, "Database connection failed");
    }
    try {

      String firstStart = "firstStart";
      SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name),
          Context.MODE_PRIVATE);
      if (sharedPreferences.getBoolean(firstStart, true)) {
        Log.d(TAG, "I am here");
        DatabaseDriverExtender.initialize(connection);

        Intent intent = new Intent(this, AccountCreationActivity.class);
        startActivity(intent);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(firstStart, Boolean.FALSE);
        editor.apply();
      } else {
        Log.d(TAG, "else I am here");
        Intent intent = new Intent(this, StoreAuthenticationActivity.class);
        startActivity(intent);

        finish();
      }
    } catch (Exception e) {
      e.printStackTrace();
      Log.e(TAG,"Something went wrong :(");
    } finally {
      try {
        connection.close();
      } catch (Exception e) {
        Log.e(TAG,"Looks like it was closed already :)");
      }
    }
  }
}
