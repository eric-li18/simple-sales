package com.b07.users;

import android.content.Context;
import com.b07.database.DatabaseSelectHelper;

public class Customer extends User {

  /**
   * Constructor for Customer with id, name, age, and address
   *
   * @param id      the user id
   * @param name    the user's name
   * @param age     the user's age
   * @param address the user's address
   */
  public Customer(int id, String name, int age, String address, Context context) {
    setId(id);
    setName(name);
    setAge(age);
    setAddress(address);
    setRoleId(DatabaseSelectHelper.getUserRoleId(id, context));
  }

  /**
   * Constructor for Customer with authentication
   *
   * @param id            the user id
   * @param name          the user's name
   * @param age           the user's age
   * @param address       the user's address
   * @param authenticated if the user has been authenticated
   */
  public Customer(int id, String name, int age, String address, boolean authenticated, Context context) {
    setId(id);
    setName(name);
    setAge(age);
    setAddress(address);
    setRoleId(DatabaseSelectHelper.getUserRoleId(id, context));
    setAuthenticate(authenticated);
  }
}
