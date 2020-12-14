package com.jobsity.bowling.repository;

import com.jobsity.bowling.domain.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends CrudRepository<Score, ScoreKey> {

    Score findByGameAndPlayer(Game game, Player player);
    List<Score> findAllByGame (Game game);
}
