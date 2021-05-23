package com.fdmgroup.factory;

import com.fdmgroup.user.User;
import com.fdmgroup.exception.InvalidRegistrationException;

/**
 * <h1>UserFactory Interface</h1>
 * This interface is a Factory containing the method responsible for the creation of Users.
 *
 * @author Justin Choi
 * @version 1.0
 * @since 23-05-2021
 */
public interface UserFactory {

	User createUser(String username, String password, String role) throws InvalidRegistrationException;

	default void setUserAttributes(User user, String username, String password, String role) {
		user.setUsername(username);
		user.setPassword(password);
		user.setRole(role);
	}

}
