package com.b07.store;

import android.content.Context;
import com.b07.database.DatabaseInsertHelper;
import com.b07.database.DatabaseUpdateHelper;
import com.b07.exceptions.AuthenticationException;
import com.b07.exceptions.UserCreationException;
import com.b07.inventory.Inventory;
import com.b07.inventory.Item;
import com.b07.users.Employee;
import com.b07.validation.Validator;
import java.io.Serializable;
import java.sql.SQLException;

public class EmployeeInterface implements Serializable {

  private Employee currentEmployee;
  private Inventory inventory;

  /**
   * Constructor for EmployeeInterface with an authenticated employee
   *
   * @param employee the employee
   * @param inventory the inventory
   */
  public EmployeeInterface(Employee employee, Inventory inventory) {
    if (employee.isAuthenticated()) {
      this.currentEmployee = employee;
      this.inventory = inventory;
    }
  }

  /**
   * Constructor for EmployeeInterface with just the inventory
   *
   * @param inventory the inventory
   */
  public EmployeeInterface(Inventory inventory) {
    this.inventory = inventory;
    this.currentEmployee = null;
  }

  /**
   * Method for setting the current authenticated employee
   *
   * @param employee the employee
   */
  public void setCurrentEmployee(Employee employee) throws AuthenticationException {
    if (employee.isAuthenticated()) {
      this.currentEmployee = employee;
    } else {
      throw new AuthenticationException();
    }
  }

  /**
   * Method to check if there is a current employee
   *
   * @return true if there is currently an employee, false otherwise
   */
  public boolean hasCurrentEmployee() {
    return this.currentEmployee != null;
  }

  /**
   * Method to insert a new item into the inventory
   *
   * @param item the item
   * @param quantity the quantity
   * @return true if the operation was sucessful, false otherwise
   */
  public boolean insertInventory(Item item, int quantity, Context context) {
    if (!Validator.validateTotalLessThanMaxItems(inventory.getTotalItems() + quantity,
        inventory.getMaxItems())) {
      System.out.println("Could not insert into the inventory. Stock overflow.");
      return false;
    }

    int inventoryId = DatabaseInsertHelper.insertInventory(item.getId(), quantity, context);
    if (inventoryId == -1) {
      return false;
    } else {
      this.inventory.updateMap(item, quantity);
      return true;
    }
  }

  /**
   * Method for restocking an existing item in the inventory
   *
   * @param item the item to restock
   * @param quantity the quantity of item to restock
   * @return true if the operation was successful, false otherwise
   */
  public boolean restockInventory(Item item, int quantity, Context context) {
    if (!Validator.validateTotalLessThanMaxItems(inventory.getTotalItems() + quantity,
        inventory.getMaxItems())) {
      System.out.println("Could not restock inventory. Stock overflow.");
      return false;
    }
    boolean completed = DatabaseUpdateHelper
        .updateInventoryQuantity(quantity, item.getId(), context);
    if (completed) {
      this.inventory.updateMap(item, quantity);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Method to create a customer
   *
   * @param name the name of the customer
   * @param age the age of the customer
   * @param address the address of the customer
   * @param password the password of the customer
   * @return the userId of the customer
   * @throws UserCreationException on failure
   */
  public int createCustomer(String name, int age, String address, String password, Context context)
      throws UserCreationException, SQLException {
    int userId = DatabaseInsertHelper.insertNewUser(name, age, address, password, context);
    if (userId == -1) {
      throw new UserCreationException();
    }

    int roleId = DatabaseInsertHelper.insertRole("CUSTOMER", context);
    if (roleId == -1) {
      throw new UserCreationException();
    }

    int userRoleId = DatabaseInsertHelper.insertUserRole(userId, roleId, context);
    if (userRoleId == -1) {
      throw new UserCreationException();
    }

    return userId;
  }

  /**
   * Method to create an employee
   *
   * @param name the name of the employee
   * @param age the age of the employee
   * @param address the address of the employee
   * @param password the password of the employee
   * @return the userId of the employee
   * @throws UserCreationException on failure
   */
  public int createEmployee(String name, int age, String address, String password, Context context)
      throws UserCreationException {
    int userId = DatabaseInsertHelper.insertNewUser(name, age, address, password, context);
    if (userId == -1) {
      throw new UserCreationException();
    }

    int roleId = DatabaseInsertHelper.insertRole("EMPLOYEE", context);
    if (roleId == -1) {
      throw new UserCreationException();
    }

    int userRoleId = DatabaseInsertHelper.insertUserRole(userId, roleId, context);
    if (userRoleId == -1) {
      throw new UserCreationException();
    }

    return userId;
  }

  public Inventory getInventory() {
    return inventory;
  }
}
