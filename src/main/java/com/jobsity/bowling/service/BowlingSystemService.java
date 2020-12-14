package com.jobsity.bowling.service;

import com.jobsity.bowling.domain.*;
import com.jobsity.bowling.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BowlingSystemService implements SystemService {

    @Autowired
    private GameService gameService;

    @Autowired
    private ScoreService<String> scoreService;

    private Game game;

    private Queue<Player> players;

    private Player previousPlayer;

    private boolean orderFound;

    @Override
    public String processGame(String filePath) throws BowlingException, IOException {
        List<String> lines = Files.lines(Paths.get(filePath)).collect(Collectors.toList());
        game = gameService.addGame(Game.builder().type(GameType.TEN_PIN_BOWLING).build());
        players = new LinkedList<>();

        for (String line : lines) {
            validateFormat(line);
            String[] data = line.split(" ");
            Player player = Player.builder().name(data[0]).build();
            addPoints(player, data[1]);
        }
        return scoreService.getResults(game);
    }

    private Player addPlayer(Player newPlayer) {
        Player player = gameService.addPlayer(game, newPlayer);
        players.add(player);
        return player;
    }

    private void addPoints(Player player, String points) throws BowlingException {
        if (players.isEmpty()) {
            Player currentPlayer = addPlayer(player);
            previousPlayer = currentPlayer;
            gameService.addPoints(game, currentPlayer, points);
        } else {
            validateOrder(player);
            Player queuePlayer = players.peek();
            if (!orderFound) {
                if (!player.getName().equals(queuePlayer.getName())) {
                    Player currentPlayer = previousPlayer;
                    if (!player.getName().equals(previousPlayer.getName())) {
                        currentPlayer = addPlayer(player);
                        previousPlayer = currentPlayer;
                    }
                    gameService.addPoints(game, currentPlayer, points);
                } else {
                    orderFound = true;
                }
            }
            if (orderFound) {
                if (!player.getName().equals(queuePlayer.getName())) {
                    throw new PlayerOrderException("The turn was for " + queuePlayer.getName());
                }
                gameService.addPoints(game, queuePlayer, points);
                if (gameService.isFrameFinished(game, queuePlayer)) {
                    players.remove();
                    players.add(queuePlayer);
                }
                previousPlayer = queuePlayer;
            }
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
        if (!player.getName().equals(previousPlayer.getName()) && !gameService.isFrameFinished(game, previousPlayer)) {
            throw new PlayerOrderException("It is still the turn of the player " + previousPlayer.getName());
        }
        if (player.getName().equals(previousPlayer.getName()) && gameService.isFrameFinished(game, previousPlayer)) {
            if (gameService.isGameFinished(game, previousPlayer)) {
                throw new FrameNumberException("The player " + previousPlayer.getName() + " has already finished the game");
            }
            if (players.size() > 1) {
                throw new PlayerOrderException("The player " + previousPlayer.getName() + " had already finished the turn");
            }
        }
    }
}
