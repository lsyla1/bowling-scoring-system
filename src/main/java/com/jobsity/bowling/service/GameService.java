package com.jobsity.bowling.service;

import com.jobsity.bowling.domain.Game;
import com.jobsity.bowling.domain.Player;

public interface GameService<T> {

    void addGame(Game game);
    void addPlayer(Player player);
    void addPlayerToGame(Game game, Player player);
    void addPoints(Game game, Player player, T points) throws Exception;
}