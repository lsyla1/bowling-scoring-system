package com.jobsity.bowling.exception;

public class PlayerOrderException extends BowlingException {

    public PlayerOrderException(String message) {
        super("The order of the players is incorrect: " + message);
    }
}
