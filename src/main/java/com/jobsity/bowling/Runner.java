package com.jobsity.bowling;

import com.jobsity.bowling.service.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
@Slf4j
public class Runner implements CommandLineRunner {

    @Autowired
    private SystemService bowlingSystemService;

    @Override
    public void run(String... args) {
        log.info("Bowling Scoring System Initialized!");
        try {
            if (args.length == 0) {
                log.error("The path of the game file to process is missing!");
            } else {
                String filePath = args[0];
                log.info("Game Path: " + filePath);
                String results = bowlingSystemService.processGame(filePath);
                log.info("\n\n" + results);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        System.exit(0);
    }
}
