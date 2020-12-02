package com.jobsity.bowling.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class GamePlayerKey implements Serializable {

    @Column(name = "game_id")
    private int gameId;

    @Column(name = "player_id")
    private int playerId;
}
