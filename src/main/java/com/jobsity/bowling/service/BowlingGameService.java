package com.jobsity.bowling.service;

import com.jobsity.bowling.domain.*;
import com.jobsity.bowling.exception.FrameNumberException;
import com.jobsity.bowling.exception.InvalidScoreException;
import com.jobsity.bowling.exception.PinsException;
import com.jobsity.bowling.repository.GameRepository;
import com.jobsity.bowling.repository.PlayerRepository;
import com.jobsity.bowling.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.jobsity.bowling.util.BowlingUtil.*;

@Service
public class BowlingGameService implements GameService<Integer> {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public void addGame(Game game) {
        gameRepository.save(game);
    }

    @Override
    public void addPlayer(Player player) {
        playerRepository.save(player);
    }

    @Override
    public void addPlayerToGame(Game game, Player player) {
        Score score = new Score();
        score.setId(new ScoreKey(game.getId(), player.getId()));
        score.setStatus(ScoreStatus.INCOMPLETE);
        score.setGame(game);
        score.setPlayer(player);
        scoreRepository.save(score);
    }

    @Override
    public void addPoints(Game game, Player player, Integer points) throws Exception {
        if (points < 0 || points > 10) {
            throw new InvalidScoreException(points + " pins registered in a throw by the player " + player.getName());
        }
        Optional<Score> scoreOptional = scoreRepository.findById(new ScoreKey(game.getId(), player.getId()));
        if (scoreOptional.isPresent()) {
            Score score = scoreOptional.get();
            Frame frame = getFrame(score);
            int frameNumber = frame.getNumber();

            List<Roll> rolls = frame.getRolls();
            int totalPins = rolls.stream().mapToInt(Roll::getPins).sum();
            if ((frameNumber < LAST_FRAME && points + totalPins > 10) || (frameNumber == LAST_FRAME && points + totalPins > 30)) {
                throw new PinsException((points + totalPins) + " pines registered in the frame " + frameNumber + " by the player " + player.getName());
            } else {
                Roll roll = new Roll();
                roll.setNumber(rolls.size() + 1);
                roll.setPins(points);
                roll.setFrame(frame);
                frame.getRolls().add(roll);
                score.getFrames().add(frame);

                if (frameNumber == LAST_FRAME && isFrameCompleted(frame)) {
                    score.setStatus(ScoreStatus.COMPLETED);
                }
                scoreRepository.save(score);
            }
        }
    }

    private Frame getFrame(Score score) throws Exception {
        Frame frame = new Frame();
        frame.setScore(score);

        List<Frame> frames = score.getFrames();
        if (!frames.isEmpty()) {
            Frame currentFrame = frames.get(frames.size() - 1);
            if (isFrameCompleted(currentFrame)) {
                if (currentFrame.getNumber() == LAST_FRAME) {
                    throw new FrameNumberException();
                } else {
                    frame.setNumber(currentFrame.getNumber() + 1);
                    return frame;
                }
            } else {
                return currentFrame;
            }
        } else {
            frame.setNumber(1);
            return frame;
        }
    }

    private boolean isFrameCompleted(Frame frame) {
        int frameNumber = frame.getNumber();
        int totalRolls = frame.getRolls().size();
        if (isStrike(frame) || isSpare(frame)) {
            return frameNumber < LAST_FRAME || (frameNumber == LAST_FRAME && totalRolls == 3);
        } else {
            return totalRolls == 2;
        }
    }
}