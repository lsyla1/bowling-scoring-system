package com.jobsity.bowling.repository;

import com.jobsity.bowling.domain.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    Player findPlayerByName (String name);
}
