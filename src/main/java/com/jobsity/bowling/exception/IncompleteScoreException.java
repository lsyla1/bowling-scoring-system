package com.jobsity.bowling.exception;

public class IncompleteScoreException extends IncompleteGameException {

    public IncompleteScoreException(String message) {
        super("There is an incomplete score: " + message);
    }
}