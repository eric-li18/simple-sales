package com.b07.store.admin;

import android.content.Context;
import android.view.View;
import com.b07.store.Seralize;
import com.b07.store.SeralizeImpl;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ImportDataButtonController implements View.OnClickListener {

  private Context appContext;

  public ImportDataButtonController(Context context) {
    appContext = context;
  }

  @Override
  public void onClick(View v) {
    Seralize backUp = new SeralizeImpl();
    backUp.seralizeDatabase(appContext);
    String ImFileName = "database_copy";
    Seralize ImFile = null;
    try {
      FileInputStream file = new FileInputStream(ImFileName);
      ObjectInputStream in = new ObjectInputStream(file);
      ImFile = (Seralize) in.readObject();

      in.close();
      file.close();
      System.out.println("Import successful");
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Import not successful, reverting to previous version");
      backUp.deseralizeDatabase(appContext);
    }
    if (ImFile != null) {
      System.out.println("Inserting into database");
      ImFile.deseralizeDatabase(appContext);
    }
  }
}
