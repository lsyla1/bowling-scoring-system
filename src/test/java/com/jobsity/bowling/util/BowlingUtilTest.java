package com.jobsity.bowling.util;

import com.jobsity.bowling.domain.Frame;
import com.jobsity.bowling.domain.Roll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BowlingUtilTest {

    private Frame frame;

    @BeforeEach
    void init() {
        frame = new Frame();
    }

    void addRollToFrame(String pins) {
        Roll roll = new Roll();
        roll.setPins(pins);
        frame.addRoll(roll);
    }

    @Test
    void isStrike() {
        addRollToFrame("10");
        assertTrue(BowlingUtil.isStrike(frame));
    }

    @Test
    void isSpare() {
        addRollToFrame("2");
        addRollToFrame("8");
        assertTrue(BowlingUtil.isSpare(frame));
    }

    @Test
    void isFrameCompletedAsNormal() {
        frame.setNumber(1);
        addRollToFrame("4");
        addRollToFrame("4");
        assertTrue(BowlingUtil.isFrameCompleted(frame));
    }

    @Test
    void isFrameCompletedAsNormalAsStrike() {
        frame.setNumber(1);
        addRollToFrame("10");
        assertTrue(BowlingUtil.isFrameCompleted(frame));
    }

    @Test
    void isFrameCompletedAsNormalAsSpare() {
        frame.setNumber(1);
        addRollToFrame("4");
        addRollToFrame("6");
        assertTrue(BowlingUtil.isFrameCompleted(frame));
    }

    @Test
    void isFrameCompletedAsTenth() {
        frame.setNumber(10);
        addRollToFrame("4");
        addRollToFrame("4");
        assertTrue(BowlingUtil.isFrameCompleted(frame));
    }

    @Test
    void isFrameCompletedAsTenthAsStrike() {
        frame.setNumber(10);
        addRollToFrame("10");
        addRollToFrame("5");
        addRollToFrame("1");
        assertTrue(BowlingUtil.isFrameCompleted(frame));
    }

    @Test
    void isFrameCompletedAsTenthAsSpare() {
        frame.setNumber(10);
        addRollToFrame("4");
        addRollToFrame("6");
        addRollToFrame("1");
        assertTrue(BowlingUtil.isFrameCompleted(frame));
    }

    @Test
    void getPureScore() {
        addRollToFrame("4");
        addRollToFrame("5");
        assertEquals(BowlingUtil.getPureScore(frame), 9);
    }

    @Test
    void getPinsOfTwoRolls() {
        addRollToFrame("4");
        addRollToFrame("3");
        addRollToFrame("1");
        assertEquals(BowlingUtil.getPinsOfTwoRolls(frame), 7);
    }
}