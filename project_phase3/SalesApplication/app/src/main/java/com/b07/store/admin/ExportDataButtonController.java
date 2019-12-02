package com.b07.store.admin;

import android.content.Context;
import android.view.View;
import android.widget.Toast;
import com.b07.store.Seralize;
import com.b07.store.SeralizeImpl;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ExportDataButtonController implements View.OnClickListener {

  private Context appContext;

  public ExportDataButtonController(Context context) {
    appContext = context;
  }

  @Override
  public void onClick(View v) {
    Seralize export = new SeralizeImpl();
    String ExFileName = "database_copy.ser";
    export.seralizeDatabase(appContext);
    try {
//      FileOutputStream file = new FileOutputStream(ExFileName);
      FileOutputStream file = appContext.openFileOutput(ExFileName, appContext.MODE_PRIVATE);
      ObjectOutputStream out = new ObjectOutputStream(file);
      out.writeObject(export);
      out.close();
      file.close();
//      System.out.println("Object has been exported");
      Toast.makeText(appContext, "Database export successful", Toast.LENGTH_SHORT).show();
    } catch (IOException e) {
//      System.out.println("Export not successful");
      Toast.makeText(appContext, "Database export unsuccessful", Toast.LENGTH_SHORT).show();
    }
  }
}
