package com.jobsity.bowling.service;

import com.jobsity.bowling.domain.Game;
import com.jobsity.bowling.domain.Player;
import com.jobsity.bowling.exception.BowlingException;

/**
 * Game Service Interface
 */
public interface GameService {

    /**
     * Add a new game
     * @param game Instance of a new game
     * @return The new game created
     */
    Game addGame(Game game);

    /**
     * Add a new player to a Game
     * @param game Existing game
     * @param player New player
     * @return The player added
     */
    Player addPlayer(Game game, Player player);

    /**
     * Add pins of a player in a game
     * @param game Existing game
     * @param player Existing player
     * @param points Points achieved in one Roll.
     * @throws BowlingException Occurss when a Bowling rule is broken
     */
    void addPoints(Game game, Player player, String points) throws BowlingException;

    /**
     * Verify is a player already finished his/her last frame
     * @param game Existing game
     * @param player Existing player
     * @return True is the last frame is completed, otherwise false
     */
    boolean isFrameFinished(Game game, Player player);

    /**
     * Verify is a player already finished the game
     * @param game Existing game
     * @param player Existing player
     * @return True is the player ended the game, otherwise false
     */
    boolean isGameFinished(Game game, Player player);
}