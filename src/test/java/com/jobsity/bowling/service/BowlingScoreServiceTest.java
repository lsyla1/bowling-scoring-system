package com.jobsity.bowling.service;

import com.jobsity.bowling.domain.*;
import com.jobsity.bowling.exception.BowlingException;
import com.jobsity.bowling.repository.FrameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BowlingScoreServiceTest {

    @Mock
    private FrameRepository frameRepository;

    @InjectMocks
    private BowlingScoreService scoreService;

    private Game game;

    private Player player;

    private Score score;

    @BeforeEach
    void init() {
        game = Game.builder().type(GameType.TEN_PIN_BOWLING).build();
        player = new Player();

        score = new Score();
        score.setId(new ScoreKey(game.getId(), player.getId()));
        score.setStatus(ScoreStatus.COMPLETED);

        addFrameWithRolls(1, new String[]{"10"});
        addFrameWithRolls(2, new String[]{"7", "3"});
        addFrameWithRolls(3, new String[]{"9", "0"});
        addFrameWithRolls(4, new String[]{"10"});
        addFrameWithRolls(5, new String[]{"0", "8"});
        addFrameWithRolls(6, new String[]{"8", "2"});
        addFrameWithRolls(7, new String[]{"F", "6"});
        addFrameWithRolls(8, new String[]{"10"});
        addFrameWithRolls(9, new String[]{"10"});
        addFrameWithRolls(10, new String[]{"10", "8", "1"});
    }

    void addFrameWithRolls(int frameNumber, String[] pins) {
        Frame frame = new Frame();
        frame.setNumber(frameNumber);

        IntStream.range(0, pins.length).forEach(i -> {
            Roll roll = new Roll();
            roll.setPins(pins[i]);
            frame.addRoll(roll);
        });

        score.addFrame(frame);
    }

    @Test
    void getPinfallsPerFrame() throws BowlingException {
        List<String> list = new ArrayList<>();
        list.add("\tX");
        list.add("7\t/");
        list.add("9\t0");
        list.add("\tX");
        list.add("0\t8");
        list.add("8\t/");
        list.add("F\t6");
        list.add("\tX");
        list.add("\tX");
        list.add("X\t8\t1");

        List<String> result = scoreService.getPinfallsPerFrame(score);

        assertEquals(list.size(), result.size());
        boolean diff = false;
        for (int i = 0; i < list.size(); i++) {
            if(!list.get(i).equals(result.get(i))) {
                diff = true;
                break;
            }
        }
        assertFalse(diff);
    }

    void getScoresPerFrame() throws BowlingException {
        when(frameRepository.findFrameByNumberAndScore(anyInt(), any(Score.class)))
                .thenAnswer(i -> score.getFrames().get(i.getArgument(0, Integer.class) - 1));

        List<String> list = new ArrayList<>();
        list.add("20");
        list.add("39");
        list.add("48");
        list.add("66");
        list.add("74");
        list.add("84");
        list.add("90");
        list.add("120");
        list.add("148");
        list.add("167");

        List<String> result = scoreService.getScoresPerFrame(score);

        assertEquals(list.size(), result.size());
        boolean diff = false;
        for (int i = 0; i < list.size(); i++) {
            if(!list.get(i).equals(result.get(i))) {
                diff = true;
                break;
            }
        }
        assertFalse(diff);
    }
}