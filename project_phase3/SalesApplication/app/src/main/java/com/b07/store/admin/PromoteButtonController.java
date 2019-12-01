package com.b07.store.admin;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.b07.R;
import com.b07.database.DatabaseSelectHelper;
import com.b07.users.Admin;
import com.b07.users.Employee;
import com.b07.users.Roles;
import com.b07.users.User;
import com.b07.validation.Validator;
import java.util.ArrayList;
import java.util.List;

;

public class PromoteButtonController implements View.OnClickListener {

  private Context appContext;
  //private int employeeId;
  private Admin admin;

  public PromoteButtonController(Context context, Admin admin) {
    appContext = context;
    //employeeId = id;
    this.admin = admin;
  }

  @Override
  public void onClick(View v) {
    EditText id = ((PromoteEmployeeUIActivity) appContext)
        .findViewById(R.id.admin_employee_id_input);
    TextView message = ((PromoteEmployeeUIActivity) appContext)
        .findViewById(R.id.promote_employee_message);
    TextView employeeView = ((PromoteEmployeeUIActivity) appContext)
        .findViewById(R.id.employee_list);

    int parsedUserId = -1;
    if (!Validator.validateEmpty(id.getText().toString())) {
      parsedUserId = Integer.parseInt(id.getText().toString());
    }

    //String TAG = "promoteActivity";
    List<Integer> roleIds = DatabaseSelectHelper.getRoleIds(appContext);
    List<Integer> userIds = new ArrayList<>();
    for (int roleId : roleIds) {
      if (DatabaseSelectHelper.getRoleName(roleId, appContext).equals(Roles.EMPLOYEE.name())) {
        int employeeRoleId = roleId;
        userIds = DatabaseSelectHelper.getUsersByRole(roleId, appContext);
      }
      if (userIds.contains(parsedUserId)) { //TODO put this in validator?
        User employee = DatabaseSelectHelper.getUserDetails(parsedUserId, appContext);
        try {
          admin.promoteEmployee((Employee) employee, appContext);
        } catch (Exception ignore) {

        }
        //Admin promotedEmployee = new Admin(employee.getId(), employee.getName(), employee.getAge(), employee.getAddress());
        //employee = null;
        int employeeRoleId = DatabaseSelectHelper
            .getRoleIdFromName(Roles.EMPLOYEE.name(), appContext);
        List<Integer> employees = DatabaseSelectHelper.getUsersByRole(employeeRoleId, appContext);

        StringBuilder employeeList = new StringBuilder();
        User user;

        for (int userId : employees) {
          user = DatabaseSelectHelper.getUserDetails(userId, appContext);
          employeeList.append(user.getId());
          employeeList.append(" - ");
          employeeList.append(user.getName());
          employeeList.append("\n");
        }

        employeeView.setText(employeeList);
        message.setText(R.string.successful_promotion);
      } else {
        message.setText(R.string.unsuccessful_promotion);
      }


    }
  }
}
