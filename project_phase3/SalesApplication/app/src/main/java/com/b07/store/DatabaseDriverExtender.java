package com.b07.store;

import com.b07.database.DatabaseDriver;
import com.b07.exceptions.ConnectionFailedException;

import java.sql.Connection;

/**
 * An extension of the DatabaseDriver to allow objects in the store package access.
 *
 * @author Joe
 */
public class DatabaseDriverExtender extends DatabaseDriver {

  /**
   * Either create a new database connection or create the initial database.
   *
   * @return the connection to the database.
   */
  protected static Connection connectOrCreateDataBase() {
    return DatabaseDriver.connectOrCreateDataBase();
  }

  /**
   * initialize the database.
   *
   * @param connection the connection to the database.
   * @return the connection.
   * @throws ConnectionFailedException if the connection is in use or unavailable.
   */
  protected static Connection initialize(Connection connection) throws ConnectionFailedException {
    return DatabaseDriver.initialize(connection);
  }

  /**
   * update the database
   *
   * @param connection the connection to the database
   * @return the connection
   * @throws ConnectionFailedException if the connection is in use or unavailable
   */
  protected static Connection update(Connection connection) throws ConnectionFailedException {
    return DatabaseDriver.updateDb(connection);
  }
}
