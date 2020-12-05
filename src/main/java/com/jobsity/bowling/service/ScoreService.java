package com.jobsity.bowling.service;

import com.jobsity.bowling.domain.Game;
import com.jobsity.bowling.domain.Player;

import java.util.List;

public interface ScoreService<T> {

    List<T> getStatusPerFrame(Game game, Player player) throws Exception;
    List<T> getScoresPerFrame(Game game, Player player) throws Exception;
}