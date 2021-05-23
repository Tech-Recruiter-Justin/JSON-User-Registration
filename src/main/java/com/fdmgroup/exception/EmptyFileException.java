package com.fdmgroup.exception;

public class EmptyFileException extends Exception{

    public EmptyFileException() {
        super("The file you are reading is empty.");
    }

}
