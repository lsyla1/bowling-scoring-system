package com.jobsity.bowling.service;

import com.jobsity.bowling.domain.*;
import com.jobsity.bowling.exception.InvalidScoreException;
import com.jobsity.bowling.exception.PinsException;
import com.jobsity.bowling.repository.GameRepository;
import com.jobsity.bowling.repository.PlayerRepository;
import com.jobsity.bowling.repository.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BowlingGameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private ScoreRepository scoreRepository;

    @InjectMocks
    private BowlingGameService gameService;

    private Game game;

    private Player player;

    private Score score;

    @BeforeEach
    void init() {
        game = Game.builder().type(GameType.TEN_PIN_BOWLING).build();
        player = Player.builder().name("ANDRES").build();

        score = new Score();
        score.setId(new ScoreKey(game.getId(), player.getId()));
        score.setStatus(ScoreStatus.INCOMPLETE);

        List<Roll> rolls = new ArrayList<>();
        Roll roll = new Roll();
        roll.setPins("9");
        rolls.add(roll);

        List<Frame> frames = new ArrayList<>();
        IntStream.rangeClosed(1, 2).forEach(value -> {
            Frame frame = new Frame();
            frame.setNumber(value);
            frame.setRolls(rolls);
            frames.add(frame);
        });

        score.setFrames(frames);
    }

    @Test
    void isGameAdded() {
        when(gameRepository.save(any(Game.class)))
                .thenReturn(game);

        Game found = gameService.addGame(game);
        assertEquals(game.getType(), found.getType());
    }

    @Test
    void isPlayerAdded() {
        when(playerRepository.findPlayerByName(anyString()))
                .thenReturn(null);
        when(playerRepository.save(any(Player.class)))
                .thenReturn(player);

        Player found = gameService.addPlayer(game, player);
        assertEquals(player.getName(), found.getName());
    }

    @Test
    void ThrownInvalidScoreExceptionWhenAddNegativePoints() {
        String pins = "-1";
        Exception exception = assertThrows(InvalidScoreException.class, () -> gameService.addPoints(game, player, pins));

        String expectedMessage = pins + " pins registered in a throw by the player " + player.getName();
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void ThrownPinsExceptionWhenAddMoreThanTenPinsPerFrame() {
        when(scoreRepository.findById(any(ScoreKey.class)))
                .thenReturn(Optional.of(score));

        Exception exception = assertThrows(PinsException.class, () -> gameService.addPoints(game, player, "2"));

        String expectedMessage = "11 pins registered in the frame 2 by the player " + player.getName();
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    void hasPlayerFinishedTurn() {
        Roll roll = new Roll();
        roll.setPins("1");

        List<Frame> frames = score.getFrames();
        Frame frame = frames.get(frames.size()-1);
        frame.addRoll(roll);
        score.addFrame(frame);

        when(scoreRepository.findByGameAndPlayer(any(Game.class), any(Player.class)))
                .thenReturn(score);

        assertTrue(gameService.isFrameFinished(game, player));
    }
}