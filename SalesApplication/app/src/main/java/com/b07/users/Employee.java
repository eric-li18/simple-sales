package com.b07.users;

public class Employee extends User {

  /**
   * Constructor for Employee with id, name, age, and address
   *
   * @param id the user id
   * @param name the user's name
   * @param age the user's age
   * @param address the user's address
   */
  public Employee(int id, String name, int age, String address) {
    setId(id);
    setName(name);
    setAge(age);
    setAddress(address);
  }

  /**
   * Constructor for Employee with id, name, age, and address
   *
   * @param id the user id
   * @param name the user's name
   * @param age the user's age
   * @param address the user's address
   */
  public Employee(int id, String name, int age, String address, boolean authenticated) {
    setId(id);
    setName(name);
    setAge(age);
    setAddress(address);
    setAuthenticate(authenticated);
  }
}
