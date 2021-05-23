package com.fdmgroup.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.fdmgroup.command.ReadCommand;
import com.fdmgroup.command.WriteCommand;
import com.fdmgroup.user.StudentUser;
import com.fdmgroup.user.User;
import com.fdmgroup.exception.InvalidRegistrationException;
import com.fdmgroup.factory.StudentFactory;
import com.fdmgroup.factory.TeacherFactory;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;

public class RegistrationControllerTest {

  @Mock
  WriteCommand mockWriteCommand = mock(WriteCommand.class);
  ReadCommand mockReadCommand = mock(ReadCommand.class);

  @Test
  public void returnAllUsers_WhenCalled_ExpectReadUserCalled() throws Exception {
    RegistrationController registrationController = new RegistrationController();
    registrationController.setReadCommand(mockReadCommand);
    registrationController.returnAllUsers();


    verify(mockReadCommand, times(1)).readUser();

  }

  @Test
  public void writeUserToFile_WhenCalled_ExpectWriteUserCalled()
      throws InvalidRegistrationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    RegistrationController registrationController = new RegistrationController();
    registrationController.setWriteCommand(mockWriteCommand);

    User mockUser = mock(User.class);
    mockUser.setUsername("xxx");
    mockUser.setPassword("xxx");
    mockUser.setRole("student");

    Method writeUserToFile = RegistrationController.class
        .getDeclaredMethod("writeUserToFile", User.class);
    writeUserToFile.setAccessible(true);
    writeUserToFile.invoke(registrationController, mockUser);

    verify(mockWriteCommand, times(1)).writeUser(mockUser);

  }

  @Test
  public void checkValidRole_InputStudent_ExpectTrue() {
    RegistrationController registrationController = new RegistrationController();
    assert registrationController.checkValidRole("student");
  }

  @Test
  public void checkValidRole_InputTeacher_ExpectTrue() {
    RegistrationController registrationController = new RegistrationController();
    assert registrationController.checkValidRole("teacher");
  }

  @Test
  public void checkValidRole_InputRandomString_ExpectFalse() {
    RegistrationController registrationController = new RegistrationController();
    assertFalse(registrationController.checkValidRole("studentsdfs"));
  }

  @Test
  public void checkValidRole_InputFalse_ExpectFalse() {
    RegistrationController registrationController = new RegistrationController();
    assertFalse(registrationController.checkValidRole(null));
  }

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  // InvalidParameterException will be sent by checkValidUserAttributesInput method
  @Test
  public void registerNewUser_inputNullUsernameOutputError() {
    RegistrationController registrationController = new RegistrationController();
    registrationController.registerNewUser(null, "1234", "trainee");

    assertTrue(errContent.toString().contains("Invalid operation during registration, restart program now..."));
  }

  @Test
  public void registerNewUser_inputNullPasswordOutputError() {
    RegistrationController registrationController = new RegistrationController();
    registrationController.registerNewUser("johncheng", null, "trainee");
    assertTrue(errContent.toString().contains("Invalid operation during registration, restart program now..."));

  }

  @Test
  public void registerNewUser_inputNullRoleOutputError() {
    RegistrationController registrationController = new RegistrationController();
    registrationController.registerNewUser("johncheng", "1234", null);
    assertTrue(errContent.toString().contains("Invalid operation during registration, restart program now..."));

  }

  // returnAllUsers
  @Test
  public void returnOneUser_WhenCalled_ExpectedSameArgsPassed() throws Exception {
    RegistrationController registrationController = new RegistrationController();
    registrationController.setReadCommand(mockReadCommand);
    registrationController.returnOneUser("john");
    verify(mockReadCommand).readUser("john");

  }

  @Test
  public void returnOneUser_WhenCalled_ExpectedReadUserCalled() throws Exception {
    RegistrationController registrationController = new RegistrationController();
    registrationController.setReadCommand(mockReadCommand);
    String john = "john";
    registrationController.returnOneUser(john);

    verify(mockReadCommand, times(1)).readUser(john);

  }

  @Test
  public void returnOneUser_InputSpecificUsername_ExceptUserInfo() {
    RegistrationController registrationController = new RegistrationController();
    User dummy3 = new StudentUser();
    dummy3.setUsername("lefteris");
    dummy3.setPassword("fdm");
    dummy3.setRole("teacher");

    User lefteris = registrationController.returnOneUser("lefteris");

    assertEquals(dummy3.toString(), lefteris.toString());

  }

  @Test
  public void returnOneUser_InputNull_ExceptNull() {
    RegistrationController registrationController = new RegistrationController();

    User user = registrationController.returnOneUser(null);
    Assert.assertNull(user);

  }

  // returnOneUser
  @Test
  public void returnOneUser_InputNone_ExpectedAllUserInfo() {

    RegistrationController registrationController = new RegistrationController();
    User[] expected = registrationController.returnAllUsers();
    User[] users = registrationController.returnAllUsers();
    for (int i = 0; i < users.length; i++) {
      assertEquals(expected[i].toString(), users[i].toString());
    }
  }

  // createSpecificFactory
  @Test
  public void createSpecificFactory_InputStudent_ExpectStudentFactory()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    RegistrationController registrationController = new RegistrationController();
    Method createSpecificFactoryMethod = RegistrationController.class
        .getDeclaredMethod("createSpecificFactory", String.class);
    createSpecificFactoryMethod.setAccessible(true);
    StudentFactory student = (StudentFactory) createSpecificFactoryMethod
        .invoke(registrationController, "student");

    Assert.assertTrue(student instanceof StudentFactory);
  }

  @Test
  public void createSpecificFactory_InputTeacher_ExpectTeacherFactory()
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    RegistrationController registrationController = new RegistrationController();
    Method createSpecificFactoryMethod = RegistrationController.class
        .getDeclaredMethod("createSpecificFactory", String.class);
    createSpecificFactoryMethod.setAccessible(true);
    TeacherFactory teacher = (TeacherFactory) createSpecificFactoryMethod
        .invoke(registrationController, "teacher");

    Assert.assertTrue(teacher instanceof TeacherFactory);
  }

  @Test
  public void createSpecificFactory_InputNull_ExpectNull()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    RegistrationController registrationController = new RegistrationController();
    Method createSpecificFactoryMethod = RegistrationController.class
        .getDeclaredMethod("createSpecificFactory", String.class);
    createSpecificFactoryMethod.setAccessible(true);
    TeacherFactory teacher = (TeacherFactory) createSpecificFactoryMethod
        .invoke(registrationController, "");

    Assert.assertNull(teacher);

  }

  @Test
  public void createSpecificFactory_InputInvalidRole_ExpectNull()
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    RegistrationController registrationController = new RegistrationController();
    Method createSpecificFactoryMethod = RegistrationController.class
        .getDeclaredMethod("createSpecificFactory", String.class);
    createSpecificFactoryMethod.setAccessible(true);
    TeacherFactory teacher = (TeacherFactory) createSpecificFactoryMethod
        .invoke(registrationController, "xxxx");

    Assert.assertNull(teacher);
  }

}



