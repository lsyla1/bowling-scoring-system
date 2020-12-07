package com.jobsity.bowling;

import com.jobsity.bowling.domain.*;
import com.jobsity.bowling.exception.*;
import com.jobsity.bowling.service.GameService;
import com.jobsity.bowling.service.ScoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.jobsity.bowling.util.BowlingUtil.TAB;
import static com.jobsity.bowling.util.BowlingUtil.DOUBLE_TAB;

@Component
@Slf4j
public class BowlingSystem implements CommandLineRunner {

    @Value("${game.file.path}")
    private String gameFilePath;

    @Autowired
    private GameService<Integer> gameService;

    @Autowired
    private ScoreService<String> scoreService;

    private Game game;

    private Queue<Player> players;

    private Player previousPlayer;

    @Override
    public void run(String... args) {
        try {
            log.info("Bowling Scoring System Initialized");
            Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource(gameFilePath)).toURI());
            log.info("Game File Path: " + path);
            List<String> lines = Files.lines(path).collect(Collectors.toList());
            game = gameService.addGame(Game.builder().type(GameType.TEN_PIN_BOWLING).build());
            players = new LinkedList<>();

            boolean establishedOrder = false;
            for (String line : lines) {
                validateFormat(line);
                String[] data = line.split(" ");
                Player player = Player.builder().name(data[0]).build();
                int points = data[1].equals("F") ? 0 : Integer.parseInt(data[1]);

                if (players.isEmpty()) {
                    Player currentPlayer = addPlayer(player);
                    gameService.addPoints(game, currentPlayer, points);
                } else {
                    validateOrder(player);
                    Player queuePlayer = Optional.ofNullable(players.peek()).orElse(Player.builder().name("").build());
                    if (!establishedOrder) {
                        if (!player.getName().equals(queuePlayer.getName())) {
                            Player currentPlayer = player.getName().equals(previousPlayer.getName()) ? previousPlayer : addPlayer(player);
                            gameService.addPoints(game, currentPlayer, points);
                        } else {
                            establishedOrder = true;
                        }
                    }
                    if (establishedOrder) {
                        if (!player.getName().equals(queuePlayer.getName())) {
                            throw new PlayerOrderException("The turn was for " + queuePlayer.getName());
                        }
                        gameService.addPoints(game, queuePlayer, points);
                        adjustOrder(queuePlayer);
                        previousPlayer = queuePlayer;
                    }
                }
            }
            printScores();
        } catch (URISyntaxException | IOException | BowlingException e) {
            log.error(e.getMessage(), e);
        }
        System.exit(0);
    }

    private Player addPlayer(Player newPlayer) {
        Player player = gameService.addPlayer(game, newPlayer);
        players.add(player);
        previousPlayer = player;
        return player;
    }

    private void adjustOrder(Player player) {
        if (gameService.isTurnEnded(game, player)) {
            player = players.poll();
            players.add(player);
        }
    }

    private void validateFormat(String line) throws IncorrectFormatException {
        String[] data = line.split(" ");
        if (data.length != 2) {
            throw new IncorrectFormatException("Player and Pins are not space-separated");
        }
        if (!data[0].matches("[a-zA-Z]+")) {
            throw new IncorrectFormatException("Player name must contain only letters");
        }
        if (!data[1].matches("[-0-9F]+")) {
            throw new IncorrectFormatException("Pins must be a numeric value or an 'F' for a foul");
        }
    }

    private void validateOrder(Player player) throws BowlingException {
        if (!player.getName().equals(previousPlayer.getName()) && !gameService.isTurnEnded(game, previousPlayer)) {
            throw new PlayerOrderException("It is still the turn of the player " + previousPlayer.getName());
        }
        if (player.getName().equals(previousPlayer.getName()) && gameService.isTurnEnded(game, previousPlayer)) {
            if(gameService.isPlayerEnded(game, previousPlayer)) {
                throw new FrameNumberException("The player " + previousPlayer.getName() + " has already finished the game");
            }
            throw new PlayerOrderException("The player " + previousPlayer.getName() + " had already finished the turn");
        }
    }

    private void printScores() throws BowlingException {
        List<String> numbers = IntStream.rangeClosed(1, 10).mapToObj(String::valueOf).collect(Collectors.toList());
        StringBuilder result = new StringBuilder();
        result.append("\nFrame").append(DOUBLE_TAB).append(String.join(DOUBLE_TAB, numbers)).append("\n");

        List<Score> scores = scoreService.getScoresByGame(game);
        for (Score score : scores) {
            List<String> playerPinfalls = scoreService.getPinfallsPerFrame(score);
            List<String> playerScores = scoreService.getScoresPerFrame(score);

            result.append(score.getPlayer().getName()).append("\n");
            result.append("Pinfalls").append(TAB).append(String.join(TAB, playerPinfalls)).append("\n");
            result.append("Score").append(DOUBLE_TAB).append(String.join(DOUBLE_TAB, playerScores)).append("\n");
        }
        System.out.println(result.toString());
    }
}
