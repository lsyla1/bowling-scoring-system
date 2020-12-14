package com.jobsity.bowling.util;

import com.jobsity.bowling.domain.Frame;
import com.jobsity.bowling.domain.Roll;

import java.util.List;

public class BowlingUtil {

    public static final int LAST_FRAME = 10;
    public static final String TAB = "\t";
    public static final String DOUBLE_TAB = "\t\t";

    public static boolean isStrike(Frame frame) {
        List<Roll> rolls = frame.getRolls();
        if (rolls.size() > 0) {
            return rolls.get(0).getPinsNumber() == 10;
        }
        return false;
    }

    public static boolean isSpare(Frame frame) {
        List<Roll> rolls = frame.getRolls();
        if (rolls.size() >= 2) {
            int roll1 = rolls.get(0).getPinsNumber();
            int roll2 = rolls.get(1).getPinsNumber();
            return roll1 < 10 & roll1 + roll2 == 10;
        }
        return false;
    }

    public static boolean isFrameCompleted(Frame frame) {
        int frameNumber = frame.getNumber();
        int totalRolls = frame.getRolls().size();
        if (isStrike(frame) || isSpare(frame)) {
            return frameNumber < LAST_FRAME || (frameNumber == LAST_FRAME && totalRolls == 3);
        } else {
            if(frameNumber < LAST_FRAME) {
                return totalRolls == 2;
            } else {
                return totalRolls == 2 && getPinsOfTwoRolls(frame) < 10 || totalRolls == 3;
            }
        }
    }

    public static int getPureScore(Frame frame) {
        return frame.getRolls().stream()
                .mapToInt(Roll::getPinsNumber)
                .sum();
    }

    public static int getPinsOfTwoRolls(Frame frame) {
        return frame.getRolls().stream()
                .limit(2)
                .mapToInt(Roll::getPinsNumber)
                .sum();
    }
}