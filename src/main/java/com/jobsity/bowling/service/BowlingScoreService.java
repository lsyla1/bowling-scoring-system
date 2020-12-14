package com.jobsity.bowling.service;

import com.jobsity.bowling.domain.*;
import com.jobsity.bowling.exception.BowlingException;
import com.jobsity.bowling.exception.IncompleteScoreException;
import com.jobsity.bowling.repository.FrameRepository;
import com.jobsity.bowling.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.jobsity.bowling.util.BowlingUtil.*;

@Service
public class BowlingScoreService implements ScoreService<String> {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private FrameRepository frameRepository;

    @Override
    public List<String> getPinfallsPerFrame(Score score) throws BowlingException {
        List<String> list = new ArrayList<>();
        if (score.getStatus() == ScoreStatus.COMPLETED) {
            List<Frame> frames = score.getFrames().stream()
                    .sorted(Comparator.comparing(Frame::getNumber))
                    .collect(Collectors.toList());

            frames.forEach(frame -> {
                String status = "";
                List<Roll> rolls = frame.getRolls();

                if (isStrike(frame)) {
                    if (frame.getNumber() < LAST_FRAME) {
                        status = TAB + "X";
                    } else {
                        status = "X" + TAB + getValue(rolls.get(1).getPins());
                    }
                } else if (isSpare(frame)) {
                    status = rolls.get(0).getPins() + TAB + "/";
                } else {
                    status = rolls.get(0).getPins() + TAB + getValue(rolls.get(1).getPins());
                }
                if (rolls.size() == 3) {
                    status += TAB + getValue(rolls.get(2).getPins());
                }
                list.add(status);
            });
        } else {
            throw new IncompleteScoreException("The player " + score.getPlayer().getName()
                    + " has not made all the throws. Current frame: " + score.getFrames().size());
        }
        return list;
    }

    @Override
    public List<String> getScoresPerFrame(Score score) throws BowlingException {
        List<String> list = new ArrayList<>();
        if (score.getStatus() == ScoreStatus.COMPLETED) {
            List<Frame> frames = score.getFrames().stream()
                    .sorted(Comparator.comparing(Frame::getNumber))
                    .collect(Collectors.toList());

            int sum = 0;
            for (Frame frame : frames) {
                sum += getPureScore(frame) + getBonus(frame);
                list.add(String.valueOf(sum));
            }
        } else {
            throw new IncompleteScoreException("The player " + score.getPlayer().getName()
                    + " has not made all the throws. Current frame: " + score.getFrames().size());
        }
        return list;
    }

    @Override
    public List<Score> getScoresByGame(Game game) {
        return scoreRepository.findAllByGame(game);
    }

    @Override
    public String getResults(Game game) throws BowlingException {
        List<String> numbers = IntStream.rangeClosed(1, 10).mapToObj(String::valueOf).collect(Collectors.toList());
        StringBuilder result = new StringBuilder();
        result.append("Frame").append(DOUBLE_TAB).append(String.join(DOUBLE_TAB, numbers)).append("\n");

        List<Score> scores = getScoresByGame(game);
        for (Score score : scores) {
            List<String> playerPinfalls = getPinfallsPerFrame(score);
            List<String> playerScores = getScoresPerFrame(score);

            result.append(score.getPlayer().getName()).append("\n");
            result.append("Pinfalls").append(TAB).append(String.join(TAB, playerPinfalls)).append("\n");
            result.append("Score").append(DOUBLE_TAB).append(String.join(DOUBLE_TAB, playerScores)).append("\n");
        }
        return result.toString();
    }

    private int getBonus(Frame frame) {
        int bonus = 0;
        if (frame.getNumber() < LAST_FRAME) {
            if (isStrike(frame)) {
                Frame nextFrame = getNextFrame(frame);
                if (isStrike(nextFrame)) {
                    if (nextFrame.getNumber() < LAST_FRAME) {
                        bonus += 10;
                        nextFrame = getNextFrame(nextFrame);
                        bonus += nextFrame.getRolls().get(0).getPins();
                    } else {
                        bonus += getPinsOfTwoRolls(nextFrame);
                    }
                } else {
                    bonus += getPinsOfTwoRolls(nextFrame);
                }
            } else if (isSpare(frame)) {
                bonus += getNextFrame(frame).getRolls().get(0).getPins();
            }
        }
        return bonus;
    }

    private Frame getNextFrame(Frame frame) {
        return frameRepository.findFrameByNumberAndScore(frame.getNumber() + 1, frame.getScore());
    }

    private String getValue(int pins) {
        return pins == 10 ? "X" : String.valueOf(pins);
    }
}