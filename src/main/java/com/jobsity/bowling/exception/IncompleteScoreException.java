package com.jobsity.bowling.exception;

public class IncompleteScoreException extends Exception {

    public IncompleteScoreException(String message) {
        super("The score is INCOMPLETE: " + message);
    }
}