package com.fdmgroup.factory;

import com.fdmgroup.user.TeacherUser;
import com.fdmgroup.user.User;
import com.fdmgroup.exception.InvalidRegistrationException;

/**
 * <h1>Create TeacherUser</h1>
 * The TeacherFactory class implements the UserFactory interface to create TeacherUser objects.
 *
 * @author Justin Choi
 * @version 1.0
 * @since 23-05-2021
 */
public class TeacherFactory implements UserFactory{

	/**
	 * The createUser method creates a TeacherUsers object only if the role is equal to teacher.
	 * If not, the InvalidRegistrationException is thrown.
	 *
	 * @param username
	 * @param password
	 * @param role
	 * @return TeacherUser A subclass object of User is returned and has the role of teacher.
	 * @throws InvalidRegistrationException If role is not teacher when trying to instantiate a TeacherUser object.
	 */
	@Override
	public User createUser(String username, String password, String role) throws InvalidRegistrationException {
		User user = new TeacherUser();
		setUserAttributes(user, username, password, role);
		if (!("teacher".equalsIgnoreCase(user.getRole()))){
			throw new InvalidRegistrationException();
		}
		return user;
	}
	
}
