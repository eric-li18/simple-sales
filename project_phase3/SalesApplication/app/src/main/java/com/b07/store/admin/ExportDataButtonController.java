package com.b07.store.admin;

import android.content.Context;
import android.view.View;
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
    String ExFileName = "Database_copy";
    export.seralizeDatabase(appContext);
    try {
      FileOutputStream file = new FileOutputStream(ExFileName);
      ObjectOutputStream out = new ObjectOutputStream(file);
      out.writeObject(export);
      out.close();
      file.close();
      System.out.println("Object has been exported");
    } catch (IOException e) {
      System.out.println("Export not successful");
    }
  }
}
