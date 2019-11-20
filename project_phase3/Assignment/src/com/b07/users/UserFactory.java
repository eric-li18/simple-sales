package com.b07.users;

/**
 * Factory class to create Users based on the factory design pattern
 *
 * @author Eric
 */
public class UserFactory {

  /**
   * Method for generating a User
   *
   * @param user    the role name
   * @param id      the userId
   * @param name    the user name
   * @param age     the user age
   * @param address the user address
   * @return the User, null if the User is not created
   */
  public static User createUser(String user, int id, String name, int age, String address) {
    if (user.equals(Roles.ADMIN.name())) {
      return new Admin(id, name, age, address);
    } else if (user.equals(Roles.EMPLOYEE.name())) {
      return new Employee(id, name, age, address);
    } else if (user.equals(Roles.CUSTOMER.name())) {
      return new Customer(id, name, age, address);
    } else {
      return null;
    }
  }
}
