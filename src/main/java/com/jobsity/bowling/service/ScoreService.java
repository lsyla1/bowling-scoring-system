package com.jobsity.bowling.service;

import com.jobsity.bowling.domain.Game;
import com.jobsity.bowling.domain.Score;
import com.jobsity.bowling.exception.BowlingException;

import java.util.List;

/**
 * Score Service Interface
 * @param <T> Data type to show the results
 */
public interface ScoreService<T> {

    /**
     * Get the pinfalls of each frame
     * @param score Score of one player
     * @return List with the details of each frame
     * @throws BowlingException Occurs when a bowling rule is broken.
     */
    List<T> getPinfallsPerFrame(Score score) throws BowlingException;

    /**
     * Get the accumulated score of each frame
     * @param score Score of one player
     * @return List of <T> values with the accomulated value of each frame.
     * @throws BowlingException Occurs when a bowling rule is broken.
     */
    List<T> getScoresPerFrame(Score score) throws BowlingException;

    /**
     * Get all the players' scores of an specific game
     * @param game Existing game
     * @return List of all scores
     */
    List<Score> getScoresByGame(Game game);

    /**
     * Get the dashboard results of a game
     * @param game Existing game
     * @return String with the dashboard results.
     * @throws BowlingException Occurs when a bowling rule is broken.
     */
    String getResults(Game game) throws BowlingException;
}