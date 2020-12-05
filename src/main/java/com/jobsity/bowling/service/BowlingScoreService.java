package com.jobsity.bowling.service;

import com.jobsity.bowling.domain.*;
import com.jobsity.bowling.exception.IncompleteScoreException;
import com.jobsity.bowling.repository.FrameRepository;
import com.jobsity.bowling.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.jobsity.bowling.util.BowlingUtil.*;

@Service
public class BowlingScoreService implements ScoreService<String> {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private FrameRepository frameRepository;

    @Override
    public List<String> getStatusPerFrame(Game game, Player player) throws Exception {
        List<String> list = new ArrayList<>();
        Optional<Score> scoreOpt = scoreRepository.findById(new ScoreKey(game.getId(), player.getId()));
        if (scoreOpt.isPresent()) {
            checkScore(scoreOpt.get());
            List<Frame> frames = scoreOpt.get().getFrames().stream()
                    .sorted(Comparator.comparing(Frame::getNumber))
                    .collect(Collectors.toList());

            frames.forEach(frame -> {
                String status = "";
                List<Roll> rolls = frame.getRolls();

                if (isStrike(frame)) {
                    if (frame.getNumber() < LAST_FRAME) {
                        status = TAB + "X";
                    } else {
                        status = "X" + TAB + rolls.get(1).getPins();
                    }
                } else if (isSpare(frame)) {
                    status = rolls.get(0).getPins() + TAB + "/";
                } else {
                    status = rolls.get(0).getPins() + TAB + rolls.get(1).getPins();
                }
                if (rolls.size() == 3) {
                    status += TAB + rolls.get(2).getPins();
                }
                list.add(status);
            });
        }
        return list;
    }

    @Override
    public List<String> getScoresPerFrame(Game game, Player player) throws Exception {
        List<String> list = new ArrayList<>();
        Optional<Score> scoreOpt = scoreRepository.findById(new ScoreKey(game.getId(), player.getId()));
        if (scoreOpt.isPresent()) {
            checkScore(scoreOpt.get());
            List<Frame> frames = scoreOpt.get().getFrames().stream()
                    .sorted(Comparator.comparing(Frame::getNumber))
                    .collect(Collectors.toList());

            int sum = 0;
            for (Frame frame : frames) {
                sum += getPureScore(frame) + getBonus(frame);
                list.add(String.valueOf(sum));
            }
        }
        return list;
    }

    private void checkScore(Score score) throws Exception {
        if (score.getStatus() == ScoreStatus.INCOMPLETE) {
            throw new IncompleteScoreException("The player " + score.getPlayer().getName()
                    + " has not made all the throws. Current frame: " + score.getFrames().size());
        }
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
}