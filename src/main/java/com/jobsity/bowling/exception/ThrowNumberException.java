package com.jobsity.bowling.exception;

public class ThrowNumberException extends Exception{

    public ThrowNumberException() {
        super("No more than 2 throws are allowed per frame. Only the frame 10 allow max 3 throws per players");
    }
}
