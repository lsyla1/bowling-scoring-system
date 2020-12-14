package com.jobsity.bowling.exception;

public abstract class IncompleteGameException extends BowlingException {

    public IncompleteGameException(String message) {
        super("The game is incomplete. " + message);
    }
}