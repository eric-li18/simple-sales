package com.b07.store;

import android.content.Context;

public interface Seralize {

  public void setRoles(Context appContext);

  public void setUserRole(Context appContext);

  public void setUsers(Context appContext);

  public void setPassword(Context appContext);

  public void setItems(Context appContext);

  public void setInventory(Context appContext);

  public void setSalesLog(Context appContext);

  public void setCarts(Context appContext);

  public void seralizeDatabase(Context appContext);

  public void deseralizeDatabase(Context appContext);
}
