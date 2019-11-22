package com.b07.users;

import android.content.Context;
import com.b07.database.DatabaseSelectHelper;
import com.b07.database.DatabaseUpdateHelper;
import java.util.List;

public class Admin extends User {


  public Admin(int id, String name, int age, String address) {
    setId(id);
    setName(name);
    setAge(age);
    setAddress(address);
  }

  public Admin(int id, String name, int age, String address, boolean authenticated) {
    setId(id);
    setName(name);
    setAge(age);
    setAddress(address);
    setAuthenticate(authenticated);
  }

  /**
   * Method to promote an employee to admin level
   *
   * @param employee the employee to be promoted
   * @return true if the operation was successful, false otherwise
   */
  public boolean promoteEmployee(Employee employee, Context context) {
    int userId = employee.getId();
    int roleId = -1;
    boolean complete;

    List<Integer> roleIds = DatabaseSelectHelper.getRoleIds(context);
    for (int id : roleIds) {
      if (DatabaseSelectHelper.getRoleName(id, context).equals((Roles.ADMIN.name()))) {
        roleId = id;
      }
    }

    complete = DatabaseUpdateHelper.updateUserRole(roleId, userId, context);
    return complete;
  }
}

