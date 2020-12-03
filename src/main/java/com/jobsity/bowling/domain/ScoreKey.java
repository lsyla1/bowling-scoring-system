package com.jobsity.bowling.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ScoreKey implements Serializable {

    @Column(name = "game_id")
    private int gameId;

    @Column(name = "player_id")
    private int playerId;
}
