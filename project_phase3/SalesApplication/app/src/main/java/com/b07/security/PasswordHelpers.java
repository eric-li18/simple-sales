package com.b07.security;

import java.security.MessageDigest;

/**
 * A class used to hash passwords and validate that entered passwords are equal to the 
 * hashed password found within the database.  Used to assist in the logging in and storage 
 * of passwords for the B07 store application.
 * @author Joe
 *
 */
public class PasswordHelpers {
  /**
   * Returns a hashed version of password to be stored in database.
   * @param password the unhashed password
   * @return the hashsed password
   */
  public static String passwordHash(String password) {
    MessageDigest messageDigest;
    try {
      messageDigest = MessageDigest.getInstance("SHA-256");
      messageDigest.update(password.getBytes("UTF-8"));
      byte[] digest = messageDigest.digest();
      return String.format("%064x", new java.math.BigInteger(1, digest));
      
    } catch (Exception e) {
      return null;
    }
  }
  
  /**
   * check if the database password matches user provided password.
   * @param dbPassword the password stored in the database.
   * @param enteredPassword the user provided password (unhashed).
   * @return true if passwords match, false otherwise.
   */
  public static boolean comparePassword(String dbPassword, String enteredPassword) {
    return dbPassword.equals(passwordHash(enteredPassword));
  }
  
}
