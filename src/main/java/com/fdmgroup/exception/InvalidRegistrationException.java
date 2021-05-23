package com.fdmgroup.exception;

public class InvalidRegistrationException extends Exception{

  public InvalidRegistrationException() {
    super("Invalid operation during registration, restart program now...");
  }

}
