package com.b07.users;

import android.content.Context;
import com.b07.database.DatabaseSelectHelper;
import com.b07.security.PasswordHelpers;
import java.io.Serializable;

/**
 * Abstract class for user
 *
 * @author Eric
 */
public abstract class User implements Serializable {

  private int id;
  private String name;
  private int age;
  private String address;
  private int roleId;
  private boolean authenticated;

  /**
   * Method for returning the user id
   *
   * @return the user id
   */
  public int getId() {
    return id;
  }

  /**
   * Method for setting or changing the user id
   *
   * @param id the user id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   * Method for returning the name of the user
   *
   * @return the name of the user
   */
  public String getName() {
    return name;
  }

  /**
   * Method for setting or changing the user's name
   *
   * @param name the name of the user
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Method for returning the user's age
   *
   * @return the user's age
   */
  public int getAge() {
    return age;
  }

  /**
   * Method for setting or changing the user's age
   *
   * @param age the user's age
   */
  public void setAge(int age) {
    this.age = age;
  }

  /**
   * Method for returning the user's address
   *
   * @return the user's address
   */
  public String getAddress() {
    return address;
  }

  /**
   * Method for setting or changing the user's address
   *
   * @param address the user's address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  public void setRoleId(int roleId) {
    this.roleId = roleId;
  }

  /**
   * Method for returning the user's role id
   *
   * @return the user's role id
   */
  public int getRoleId() {
    return roleId;
  }

  /**
   * Method for authenticating the user's password
   *
   * @param password the user's entered password
   * @return true if the entered password matches the password in the database, false otherwise
   */
  public final boolean authenticate(String password, Context context) {
    String dbPassword = DatabaseSelectHelper.getPassword(id, context);
    return PasswordHelpers.comparePassword(dbPassword, password);
  }

  /**
   * Method for setting or changing authentication status
   *
   * @param authenticated their authentication
   */
  protected void setAuthenticate(boolean authenticated) {
    this.authenticated = authenticated;
  }

  /**
   * Method for returning whether the user is authenticated
   *
   * @return true if the user is authenticated, false otherwise
   */
  public boolean isAuthenticated() {
    return authenticated;
  }
}
