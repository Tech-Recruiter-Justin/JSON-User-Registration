package com.fdmgroup.factory;

import com.fdmgroup.user.StudentUser;
import com.fdmgroup.user.User;
import com.fdmgroup.exception.InvalidRegistrationException;

/**
 * <h1>Create StudentUser</h1>
 * The StudentFactory class implements the UserFactory interface to create StudentUser objects.
 *
 * @author Justin Choi
 * @version 1.0
 * @since 23-05-2021
 */
public class StudentFactory implements UserFactory {

	/**
	 * The createUser method creates a StudentUser object only if the role is equal to student.
	 * If not, the InvalidRegistrationException is thrown.
	 *
	 * @param username
	 * @param password
	 * @param role
	 * @return StudentUser A subclass object of User is returned and has the role of student.
	 * @throws InvalidRegistrationException If role is not student when trying to instantiate a StudentUser object.
	 */
	@Override
	public User createUser(String username, String password, String role) throws InvalidRegistrationException {
		User user = new StudentUser();
		setUserAttributes(user, username, password, role);
		if (!("student".equalsIgnoreCase(user.getRole()))){
			throw new InvalidRegistrationException();
		}
		return user;
	}
	
}