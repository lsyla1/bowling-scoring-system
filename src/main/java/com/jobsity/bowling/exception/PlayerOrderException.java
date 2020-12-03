package com.jobsity.bowling.exception;

public class PlayerOrderException extends Exception {

    public PlayerOrderException() {
        super("The order of the players is wrong!");
    }
}
