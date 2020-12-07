package com.jobsity.bowling.repository;

import com.jobsity.bowling.domain.Frame;
import com.jobsity.bowling.domain.Score;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrameRepository extends CrudRepository<Frame, Integer> {

    Frame findFrameByNumberAndScore(int number, Score score);
}