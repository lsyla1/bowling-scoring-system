package com.jobsity.bowling.repository;

import com.jobsity.bowling.domain.ScoreKey;
import com.jobsity.bowling.domain.Score;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends CrudRepository<Score, ScoreKey> {
}
