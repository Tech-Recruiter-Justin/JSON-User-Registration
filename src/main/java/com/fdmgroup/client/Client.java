package com.fdmgroup.client;

import com.fdmgroup.controller.RegistrationController;
import com.fdmgroup.user.User;
import java.util.Scanner;

public class Client {

  public static void main(String[] args) {
    RegistrationController registrationController = new RegistrationController();

    System.out.println("Welcome to User Registration System!");

    mainLoop:
    while (true) {

      Scanner scanner = new Scanner(System.in);
      System.out.println("Please type 1/2/3 for these options: ");
      System.out.println("1 : Register new user");
      System.out.println("2 : Read all users info from database");
      System.out.println("3 : Read a specific user info from database");
      System.out.println("q : Exit");

      String userOptionInput = scanner.next();

      switch (userOptionInput) {

        case ("1"):
          // Query for user registration input
          System.out.println("Please type in username: ");
          String usernameInput = scanner.next();
          System.out.println("Please type in password: ");
          String passwordInput = scanner.next();
          System.out.println("Please type in role (student/teacher): ");
          String roleInput = scanner.next();

          // Defend from NullPointerException
          if (!registrationController.checkValidRole(roleInput)) {
            System.out.println(
                "Invalid role input, must be student or teacher, please try again");
            System.out.println();
            break;
          }

          // Register new user in memory
          registrationController.registerNewUser(usernameInput, passwordInput, roleInput);
          break;

        case ("2"):
          // Print all users in the JSON file
          for (User user : registrationController.returnAllUsers()) {
            System.out.println(user);
          }
          break;

        case ("3"):
          // Prompt for username to check
          System.out.println("Please type in the username: ");

          // Display information of the specified user
          System.out.println(registrationController.returnOneUser(scanner.next()));
          break;

        case ("q"):
          // Exit program
          break mainLoop;

        default:
          System.out.println("Invalid Input: No such option, please try again");
          break;

      }

      // Check if user wants to terminate the app
      System.out.println("Any other operations? : y/n");
      String yesNo = scanner.next();

      // Only "y" or "Y" will continue the program, other inputs will exit
      if (!("y".equalsIgnoreCase(yesNo))) {
        break;
      }

    }

    System.out.println();
    System.out.println("Exit User Registration System!");

  }
}
