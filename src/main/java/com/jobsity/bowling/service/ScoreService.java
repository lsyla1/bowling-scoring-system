package com.jobsity.bowling.service;

import com.jobsity.bowling.domain.Game;
import com.jobsity.bowling.domain.Score;
import com.jobsity.bowling.exception.BowlingException;

import java.util.List;

public interface ScoreService<T> {

    List<T> getPinfallsPerFrame(Score score) throws BowlingException;
    List<T> getScoresPerFrame(Score score) throws BowlingException;
    List<Score> getScoresByGame(Game game);
    String getResults(Game game) throws BowlingException;
}