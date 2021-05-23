package com.fdmgroup.factory;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.Test;

import com.fdmgroup.user.StudentUser;
import com.fdmgroup.user.TeacherUser;
import com.fdmgroup.user.User;
import com.fdmgroup.exception.InvalidRegistrationException;

public class StudentAndTeacherFactoryTest {
	
	@Test
	public void createStudentUserWithAttributesSet() throws InvalidRegistrationException {
		
		StudentFactory studentFactory = new StudentFactory();
		
		User studentUser = studentFactory.createUser("zach", "1234", "student");
		
		assertThat(studentUser, instanceOf(StudentUser.class));
		assertEquals("student", studentUser.getRole());
	}
	
	@Test
	public void createTeacherUserWithAttributesSet() throws InvalidRegistrationException {
		
		TeacherFactory teacherFactory = new TeacherFactory();
		
		User teacherUser = teacherFactory.createUser("lefteris", "1234", "teacher");
		
		assertThat(teacherUser, instanceOf(TeacherUser.class));
		assertEquals("teacher", teacherUser.getRole());
	}
	
	@Test(expected = InvalidRegistrationException.class)
	public void createStudentUserWithWrongRoleSet_Expect_InvalidRegistrationException() throws InvalidRegistrationException {
		
		StudentFactory studentFactory = new StudentFactory();
		User studentUser = studentFactory.createUser("zach", "1234", "Notstudent");
		
	}
	
	
	@Test(expected = InvalidRegistrationException.class)
	public void createTeacherUserWithWrongRoleSet_Expect_InvalidRegistrationException() throws InvalidRegistrationException {
		
		StudentFactory studentFactory = new StudentFactory();
		User studentUser = studentFactory.createUser("zach", "1234", "NotTeacher");
		
	}
	
}
