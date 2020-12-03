package com.jobsity.bowling.exception;

public class InvalidScoreException extends Exception{

    public InvalidScoreException() {
        super("The number of fallen pines for each throw must be a positive number");
    }
}
