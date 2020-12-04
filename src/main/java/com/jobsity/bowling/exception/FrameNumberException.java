package com.jobsity.bowling.exception;

public class FrameNumberException extends Exception {

    public FrameNumberException() {
        super("No more than 10 frames are allowed per player");
    }
}
