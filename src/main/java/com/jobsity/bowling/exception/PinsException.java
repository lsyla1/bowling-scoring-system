package com.jobsity.bowling.exception;

public class PinsException extends Exception{

    public PinsException(String message) {
        super("No more than 10 pins are allowed per player turn: " + message);
    }
}
