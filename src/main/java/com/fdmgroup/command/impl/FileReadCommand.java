package com.fdmgroup.command.impl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fdmgroup.command.ReadCommand;
import com.fdmgroup.user.StudentUser;
import com.fdmgroup.user.User;
import com.fdmgroup.exception.EmptyFileException;

import java.io.File;
import java.io.IOException;

/**
 * <h1>Read Content From JSON File</h1>
 * The FileReadCommand class parses a JSON file from a specific path
 * and returns a single User object or an array of User objects.
 * <p>
 * <b>Note:</b> The default path of this programme is hardcoded for demo purpose and
 * the user does not have to worry about the underlying mechanism.
 * However, this can be easily modified to accept different paths.
 *
 * @author  Justin Choi
 * @version 1.0
 * @since   2021-05-23
 */
public class FileReadCommand implements ReadCommand {

    final private String path;

    public FileReadCommand (String path) {
        this.path = path;
    }

    /**
     * The method is used to check a specified user in the JSON file.
     * The console will show the username, password and role of the user in the file.
     *
     * @param username
     * @return User The returned User object will have a matching username to the input.
     * @throws EmptyFileException On reading an empty file.
     */
    @Override
    public User readUser(String username) throws EmptyFileException {
        User[] users = readUser();
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    /**
     * The method is used to read all users in the JSON file.
     * The console will print all JSONObjects in the JSONArray one by one.
     *
     * @return User[] The returned User Array contains all User objects parsed from the JSON file.
     * @throws EmptyFileException On reading an empty file.
     */
    @Override
    public User[] readUser() throws EmptyFileException {
        String path = getPath();
        ObjectMapper mapper = new ObjectMapper();

        if (new File(path).length() == 0) {
            throw new EmptyFileException();
        }

        try {
            User[] user = mapper.readValue(new File(path), StudentUser[].class);
            return user;
        } catch (JsonParseException e) {
            System.out.println("Your file is not a JSON");
            return null;
        } catch (JsonMappingException e) {
            System.out.println("JSON mapping does not match the User class");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Getter for file path
    public String getPath() {
        return this.path;
    }

}