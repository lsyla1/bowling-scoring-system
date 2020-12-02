package com.jobsity.bowling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BowlingSystem implements CommandLineRunner {

    @Value("${game.file.path}")
    private String gameFilePath;

    @Override
    public void run(String... args) {
        log.info("Bowling system created with game file path: " + gameFilePath);
    }
}
