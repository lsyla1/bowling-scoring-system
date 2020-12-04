package com.jobsity.bowling;

import com.jobsity.bowling.domain.*;
import com.jobsity.bowling.service.GameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BowlingSystem implements CommandLineRunner {

    @Value("${game.file.path}")
    private String gameFilePath;

    @Autowired
    private GameService<Integer> gameService;

    @Override
    public void run(String... args) {
        log.info("Bowling system created with game file path: " + gameFilePath);
    }
}
