package com.jobsity.bowling.exception;

public class PinfallsException extends Exception{

    public PinfallsException() {
        super("No more than t10 pins are allowed per player turn!");
    }
}
