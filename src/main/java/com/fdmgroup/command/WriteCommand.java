package com.fdmgroup.command;

import com.fdmgroup.user.User;
import com.fdmgroup.exception.InvalidRegistrationException;

/**
 * <h1>WriteCommand Interface</h1>
 * This interface contains the method responsible for writing new information into the JSON file.
 *
 * @author Justin Choi
 * @version 1.0
 * @since 23-05-2021
 */
public interface WriteCommand {
    void writeUser(User user) throws InvalidRegistrationException;
}
