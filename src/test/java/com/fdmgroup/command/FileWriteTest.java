package com.fdmgroup.command;

import static org.junit.Assert.assertNotEquals;

import com.fdmgroup.user.StudentUser;
import org.junit.Test;
import com.fdmgroup.user.User;
import com.fdmgroup.command.impl.*;

public class FileWriteTest {

	@Test
	//Test for when we want to write in a user that is not in the JSON file
	public void passInNewUserAppendToJSON() throws Exception {

		//Create a user that is not in the JSON file
		User angelinajolie = new StudentUser();
		angelinajolie.setUsername("angelinajolie");
		angelinajolie.setPassword("1234");
		angelinajolie.setRole("student");

		//I use the FileReadCommand to convert the current JSON file into Java and store it in a variable
		FileReadCommand read = new FileReadCommand("src/json/sample.json");
		User[] originalSetOfUsers = read.readUser();
		int originalLength = originalSetOfUsers.length;

		//Input user using the FileWriteCommand
		FileWriteCommand write = new FileWriteCommand();
		write.writeUser(angelinajolie);

		//Assert
		FileReadCommand read2 = new FileReadCommand("src/json/sample.json");
		User[] newSetOfUsers = read2.readUser();
		int newLength = newSetOfUsers.length;
		assertNotEquals(newLength, originalLength);

	}
}
