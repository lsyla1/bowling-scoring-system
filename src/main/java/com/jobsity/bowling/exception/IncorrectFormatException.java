package com.jobsity.bowling.exception;

public class IncorrectFormatException extends Exception{

    public IncorrectFormatException() {
        super("The input data is incorrectly formatted");
    }
}
