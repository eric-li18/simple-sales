package com.b07.store.admin;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.b07.store.Seralize;
import com.b07.store.SeralizeImpl;
import com.b07.store.login.StoreAuthenticationActivity;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ImportDataButtonController extends AppCompatActivity implements View.OnClickListener {

  private Context appContext;

  public ImportDataButtonController(Context context) {
    appContext = context;
  }

  @Override
  public void onClick(View v) {
    Seralize backUp = new SeralizeImpl();
    backUp.seralizeDatabase(appContext);
    String ImFileName = "database_copy.ser";
    Seralize ImFile = new SeralizeImpl();
    boolean back = false;
    try {
      FileInputStream file = appContext.openFileInput(ImFileName);
      ObjectInputStream in = new ObjectInputStream(file);
      ImFile = (Seralize) in.readObject();

      in.close();
      file.close();

      Toast.makeText(appContext, "Database import successful", Toast.LENGTH_SHORT).show();
//      System.out.println("Import successful");
    } catch (IOException | ClassNotFoundException e) {
      Toast.makeText(appContext, "Database import unsuccessful", Toast.LENGTH_SHORT).show();
//      System.out.println("Import not successful, reverting to previous version");
      back = true;
      e.printStackTrace();
    }
    if (ImFile != null && !back) {
      ImFile.deseralizeDatabase(appContext);
    } else if (back) {
      backUp.deseralizeDatabase(appContext);
    }
    Toast toast = Toast.makeText(appContext, "Logging out...", Toast.LENGTH_SHORT);
    toast.show();
    Intent intent = new Intent(appContext, StoreAuthenticationActivity.class);
    intent.putExtra("backPress", "no");
    appContext.startActivity(intent);
  }
}
