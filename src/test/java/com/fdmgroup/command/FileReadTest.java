package com.fdmgroup.command;

import com.fdmgroup.command.impl.FileReadCommand;
import com.fdmgroup.user.StudentUser;
import com.fdmgroup.user.TeacherUser;
import com.fdmgroup.user.User;
import com.fdmgroup.exception.EmptyFileException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FileReadTest {

    @Test
    public void shouldReturnNullWhenFileIsNotJson() throws EmptyFileException{

        FileReadCommand read = new FileReadCommand("src/json/incorrectFile.txt");
        User[] users = read.readUser();
        Assert.assertNull(users);
    }

    @Test
    public void shouldReturnNullWhenJsonDoesNotMatch() throws EmptyFileException{

        FileReadCommand read = new FileReadCommand("src/json/unmatched.json");
        User[] users = read.readUser();
        Assert.assertNull(users);
    }

    @Rule
    public ExpectedException expectedEx1 = ExpectedException.none();
    @Test
    public void shouldThrowFileEmptyExceptionWhenFileIsEmpty() throws EmptyFileException {

        expectedEx1.expect(EmptyFileException.class);
        expectedEx1.expectMessage("The file you are reading is empty.");

        FileReadCommand read = new FileReadCommand("src/json/empty.json");
        User[] users = read.readUser();
    }

    @Test
    public void passInJSONReturnsUser() throws EmptyFileException {

        User[] users = new User[3];

        User justin = new StudentUser();
        justin.setUsername("justinchoi");
        justin.setPassword("abcd");
        justin.setRole("student");
        users[0] = justin;

        User john = new StudentUser();
        john.setUsername("johncheng");
        john.setPassword("1234");
        john.setRole("student");
        users[1] = john;

        User lefteris = new TeacherUser();
        lefteris.setUsername("lefteris");
        lefteris.setPassword("fdm");
        lefteris.setRole("teacher");
        users[2] = lefteris;

        FileReadCommand read = new FileReadCommand("src/json/sample.json");
        User[] user = read.readUser();

        for (int i = 0; i < users.length; i++) {
            System.out.println(users[i].toString());
            System.out.println(user[i].toString());
            Assert.assertEquals(users[i].toString(), user[i].toString());
        }
    }

    @Test
    public void passInUserNameReturnSpecificUserObject() throws EmptyFileException {

        User john = new StudentUser();
        john.setUsername("johncheng");
        john.setPassword("1234");
        john.setRole("student");

        FileReadCommand read = new FileReadCommand("src/json/sample.json");
        User user = read.readUser("johncheng");
        System.out.println(john.toString());
        System.out.println(user.toString());

        Assert.assertEquals(john.toString(), user.toString());
    }

}