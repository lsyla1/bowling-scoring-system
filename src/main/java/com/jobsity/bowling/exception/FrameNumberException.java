package com.jobsity.bowling.exception;

public class FrameNumberException extends BowlingException {

    public FrameNumberException(String message) {
        super("No more than 10 frames are allowed per player: " + message);
    }
}
