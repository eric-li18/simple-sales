package com.b07.users;

import com.b07.database.helper.DatabaseSelectHelper;

public class Customer extends User {

  /**
   * Constructor for Customer with id, name, age, and address
   *
   * @param id      the user id
   * @param name    the user's name
   * @param age     the user's age
   * @param address the user's address
   */
  public Customer(int id, String name, int age, String address) {
    setId(id);
    setName(name);
    setAge(age);
    setAddress(address);
    setRoleId(DatabaseSelectHelper.getUserRoleId(id));
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
  public Customer(int id, String name, int age, String address, boolean authenticated) {
    setId(id);
    setName(name);
    setAge(age);
    setAddress(address);
    setRoleId(DatabaseSelectHelper.getUserRoleId(id));
    setAuthenticate(authenticated);
  }
}
