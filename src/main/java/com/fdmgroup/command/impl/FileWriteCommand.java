package com.fdmgroup.command.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fdmgroup.command.WriteCommand;
import com.fdmgroup.user.User;
import com.fdmgroup.exception.InvalidRegistrationException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <h1>Adds New Users To Current JSON File</h1>
 * The FileWriteCommand class takes in User objects and writes them into a JSON file in the correct format.
 * <p>
 * The JSON file has a JSONArray which contains all user information.
 * 
 * @author Justin Choi
 * @version 1.0
 * @since 23-05-2021
 */
public class FileWriteCommand implements WriteCommand {

  /**
   * Converts a User object into JSON format and saves it into the specified JSON file.
   *
   * @param user
   * @throws InvalidRegistrationException
   */
  @Override
  public void writeUser(User user) throws InvalidRegistrationException {

    File file = new File("src/json/sample.json");
    long fileLength = file.length();

    try {
      createJsonFileIfNotExist(file);
      if (user != null) {
        appendUserWithJsonFormat(new ObjectMapper(), file, fileLength, user);
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("JSON file creation failed, please try again");
      throw new InvalidRegistrationException();
    }
  }

  /**
   * Converts a User object into JSON String using an ObjectMapper.
   *
   * @param mapper
   * @param user
   * @throws JsonProcessingException If POJO cannot be parsed into JSON String.
   */
  public String convertUserToJsonFormatString(ObjectMapper mapper, User user) throws JsonProcessingException {
    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
  }

  /**
   * Creates the file path if it does not exist.
   *
   * @param file
   * @throws IOException If IO fails to create file.
   */
  public void createJsonFileIfNotExist(File file) throws IOException {
    if (!(file.exists())) {
      file.createNewFile();
    }
  }

  /**
   * Appends the specified character to a JSON file.
   *
   * @param path
   * @param outputChar
   * @throws IOException When append character fails.
   */
  public void printCharToJsonFile(Path path, String outputChar) throws IOException {
    Files.write(path, outputChar.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
  }

  /**
   * Append new user information to the JSON file.
   *
   * @param mapper
   * @param file
   * @param fileLength
   * @param user
   * @throws InvalidRegistrationException If the append operation fails.
   */
  public void appendUserWithJsonFormat(ObjectMapper mapper, File file, long fileLength, User user)
      throws InvalidRegistrationException {

    try {
      Path path = file.toPath();
      Scanner input = new Scanner(file);
      StringBuffer temp = new StringBuffer();
      String json = convertUserToJsonFormatString(mapper, user);

      if (fileLength != 0) {
        // If file is not empty, remove "]"
        while (input.hasNext()) {
          // go through each line to remove every ], then add the new line into temp file
          String s = input.nextLine();
          temp.append(s.replace("]", "") + "\r\n");
        }
        // Print the content from temp file into json file
        PrintWriter output = new PrintWriter(file);
        output.print(temp);
        input.close();
        output.close();
        printCharToJsonFile(path, ",");
      } else {
        // If .json file is empty, add open bracket
        printCharToJsonFile(path, "[");
      }
      // Append the user object in JSON format
      Files.write(path, Arrays.asList(json), StandardOpenOption.APPEND);
      // close square
      printCharToJsonFile(path, "]");
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("Failed to write user into JSON file, please try again");
      throw new InvalidRegistrationException();
    }

  }

}


