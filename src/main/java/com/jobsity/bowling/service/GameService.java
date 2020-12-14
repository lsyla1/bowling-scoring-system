package com.jobsity.bowling.service;

import com.jobsity.bowling.domain.Game;
import com.jobsity.bowling.domain.Player;
import com.jobsity.bowling.exception.BowlingException;

public interface GameService {

    Game addGame(Game game);
    Player addPlayer(Game game, Player player);
    void addPoints(Game game, Player player, String points) throws BowlingException;
    boolean isFrameFinished(Game game, Player player);
    boolean isGameFinished(Game game, Player player);
}