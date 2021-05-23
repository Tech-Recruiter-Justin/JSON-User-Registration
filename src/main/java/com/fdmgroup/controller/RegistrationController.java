package com.fdmgroup.controller;

import com.fdmgroup.command.ReadCommand;
import com.fdmgroup.command.WriteCommand;
import com.fdmgroup.command.impl.FileReadCommand;
import com.fdmgroup.command.impl.FileWriteCommand;
import com.fdmgroup.user.User;
import com.fdmgroup.exception.EmptyFileException;
import com.fdmgroup.exception.InvalidRegistrationException;
import com.fdmgroup.factory.StudentFactory;
import com.fdmgroup.factory.TeacherFactory;
import com.fdmgroup.factory.UserFactory;

/**
 * <h1>Registration Controller</h1>
 * The Controller uses ReadCommand, WriteCommand and UserFactory
 * to implement three main functionality such as registering new user,
 * read all users or get a specific user from a JSON file where it
 * stores all users the system saved in the past.
 *
 * @author  John Cheng
 * @version 1.0
 * @since   2021-05-20
 */
public class RegistrationController {

  private ReadCommand readCommand;
  private WriteCommand writeCommand;
  private UserFactory userFactory;

  public RegistrationController() {

    this.readCommand = new FileReadCommand("src/json/sample.json");
    this.writeCommand = new FileWriteCommand();
  }

  /**
   * Main procedure to create a new user based on user inputs of username,
   * password and role. If all the inputs are validated, a User will be created
   * and write into JSON file.
   *
   * @param username
   * @param password
   * @param role
   */
  public void registerNewUser(String username, String password, String role) {

    setUserFactory(createSpecificFactory(role));

    try {

      checkValidUserAttributesInput(username, password, role);
      User user = this.userFactory.createUser(username, password, role);

      if (checkDuplicates(user)) {

        System.err.println("Duplicate user exist, cannot save this user");
        throw new InvalidRegistrationException();

      }
      writeUserToFile(user);

    } catch (InvalidRegistrationException e) {

      e.printStackTrace();
      System.err.println("Registration process went wrong, please try again");

    }
  }


  /**
   * This uses writeUser method in writeCommand to output the User datas into
   * a JSON file
   *
   * @param user
   * @throws InvalidRegistrationException
   */
  private void writeUserToFile(User user) throws InvalidRegistrationException {

    this.writeCommand.writeUser(user);
  }

  /**
   * This method throws InvalidRegistrationException Exception if any input is null.
   * This is a defense from NullPointerException
   *
   * @param username
   * @param password
   * @param role
   * @throws InvalidRegistrationException If any param is null.
   */
  private void checkValidUserAttributesInput(String username, String password, String role)
      throws InvalidRegistrationException {

    if (username == null || password == null || role == null) {
      throw new InvalidRegistrationException();
    }
  }

  /**
   * Return either StudentFactory or TeacherFactory based on the input,
   * which should be either "student" or "teacher
   *
   * @param role
   * @return UserFactory
   */
  private UserFactory createSpecificFactory(String role) {

    if (role == null) {
      return null;
    }
    if ("student".equalsIgnoreCase(role)) {
      return new StudentFactory();
    }
    if ("teacher".equalsIgnoreCase(role)) {
      return new TeacherFactory();
    }

    return null;
  }

  /**
   * This method return true if the role input is either "student" or "teacher",
   * otherwise return false
   *
   * @param role
   * @return boolean
   */
  public boolean checkValidRole(String role) {
    return "student".equalsIgnoreCase(role) || "teacher".equalsIgnoreCase(role);
  }

  /**
   * This method return all users from the JSON file as a array of User
   *
   * @return User[]
   */
  public User[] returnAllUsers() {


    try {
      return this.readCommand.readUser();
    } catch (EmptyFileException e) {
      e.printStackTrace();
      System.err.println("Reading process interrupted, please try again");

    }
    return null;
  }

  /**
   * This method return a user which matches with the username input
   *
   * @param username
   * @return User
   */
  public User returnOneUser(String username) {

    try {
      return this.readCommand.readUser(username);
    } catch (EmptyFileException e) {
      e.printStackTrace();
      System.err.println("Reading process interrupted, please try again");
    }
    return null;
  }

  public void setReadCommand(ReadCommand readCommand) {
    this.readCommand = readCommand;
  }

  public void setWriteCommand(WriteCommand writeCommand) {
    this.writeCommand = writeCommand;
  }

  public void setUserFactory(UserFactory userFactory) {
    this.userFactory = userFactory;
  }

  /**
   * If there is a same User existed in the JSON file,
   * it will return true, otherwise return false
   *
   * @param user
   * @return boolean
   */
  public boolean checkDuplicates(User user)  {
    User[] users = new User[0];
    try {
      users = this.readCommand.readUser();
    } catch (EmptyFileException e) {
      e.printStackTrace();
      System.err.println("Reading process interrupted, please try again");

    }

    for (User u: users) {

      // if anyone match the same username
      if (u.getUsername().equals(user.getUsername())) {
        return true;
      }
		}

    // if no one matches username
    return false; // false, there no duplicate, we can put it
  }
}
