package com.jobsity.bowling.exception;

public class IncompleteGameException extends Exception {

    public IncompleteGameException() {
        super("The game is incomplete for some players");
    }
}