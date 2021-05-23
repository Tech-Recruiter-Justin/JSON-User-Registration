package com.fdmgroup.command;

import com.fdmgroup.user.User;
import com.fdmgroup.exception.EmptyFileException;

/**
 * <h1>ReadCommand Interface</h1>
 * This interface contains the method responsible for reading information from the JSON file.
 *
 * @author Justin Choi
 * @version 1.0
 * @since 23-05-2021
 */
public interface ReadCommand {
    User[] readUser() throws EmptyFileException;
    User readUser(String username) throws EmptyFileException;

}
