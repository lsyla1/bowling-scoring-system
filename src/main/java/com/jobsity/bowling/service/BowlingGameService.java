package com.jobsity.bowling.service;

import com.jobsity.bowling.domain.*;
import com.jobsity.bowling.exception.BowlingException;
import com.jobsity.bowling.exception.InvalidScoreException;
import com.jobsity.bowling.exception.PinsException;
import com.jobsity.bowling.repository.GameRepository;
import com.jobsity.bowling.repository.PlayerRepository;
import com.jobsity.bowling.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    public Game addGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public Player addPlayer(Game game, Player player) {
        Player rPlayer = playerRepository.findPlayerByName(player.getName());
        if (Objects.isNull(rPlayer)) {
            rPlayer = playerRepository.save(player);
        }
        Score score = new Score();
        score.setId(new ScoreKey(game.getId(), rPlayer.getId()));
        score.setStatus(ScoreStatus.INCOMPLETE);
        score.setGame(game);
        score.setPlayer(rPlayer);
        scoreRepository.save(score);
        return rPlayer;
    }

    @Override
    public void addPoints(Game game, Player player, Integer points) throws BowlingException {
        if (points < 0 || points > 10) {
            throw new InvalidScoreException(points + " pins registered in a throw by the player " + player.getName());
        }
        Optional<Score> scoreOptional = scoreRepository.findById(new ScoreKey(game.getId(), player.getId()));
        if (scoreOptional.isPresent()) {
            Score score = scoreOptional.get();
            Frame frame = getFrameToUse(score);
            int frameNumber = frame.getNumber();

            List<Roll> rolls = frame.getRolls();
            int pinsAllRolls = rolls.stream().mapToInt(Roll::getPins).sum();
            if ((frameNumber < LAST_FRAME && points + pinsAllRolls > 10) || (frameNumber == LAST_FRAME && points + pinsAllRolls > 30)) {
                throw new PinsException((points + pinsAllRolls) + " pines registered in the frame " + frameNumber + " by the player " + player.getName());
            } else {
                Roll roll = new Roll();
                roll.setNumber(rolls.size() + 1);
                roll.setPins(points);
                roll.setFrame(frame);
                frame.addRoll(roll);
                score.addFrame(frame);

                if (frameNumber == LAST_FRAME && isFrameCompleted(frame)) {
                    score.setStatus(ScoreStatus.COMPLETED);
                }
                scoreRepository.save(score);
            }
        }
    }

    @Override
    public boolean isTurnEnded(Game game, Player player) {
        Score score = scoreRepository.findByGameAndPlayer(game, player);
        return isFrameCompleted(score.getLastFrame());
    }

    @Override
    public boolean isPlayerEnded(Game game, Player player) {
        Score score = scoreRepository.findByGameAndPlayer(game, player);
        return score.getStatus() == ScoreStatus.COMPLETED;
    }

    private Frame getFrameToUse(Score score) {
        Frame frame = new Frame();
        frame.setScore(score);

        Frame currentFrame = score.getLastFrame();
        if (Objects.nonNull(currentFrame)) {
            if (isFrameCompleted(currentFrame)) {
                frame.setNumber(currentFrame.getNumber() + 1);
                return frame;
            } else {
                return currentFrame;
            }
        } else {
            frame.setNumber(1);
            return frame;
        }
    }
}