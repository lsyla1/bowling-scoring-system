package com.jobsity.bowling.exception;

public class IncorrectFormatException extends BowlingException {

    public IncorrectFormatException(String message) {
        super("The input data is incorrectly formatted: " + message);
    }
}
