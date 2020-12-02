package com.jobsity.bowling.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "scores")
public class Score {

    @EmbeddedId
    private GamePlayerKey id;

    @Column(name = "status", nullable = false)
    private ScoreStatus status;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "score")
    private List<Frame> frames;
}
