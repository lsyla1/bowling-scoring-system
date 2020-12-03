package com.jobsity.bowling.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "scores")
public class Score {

    @EmbeddedId
    private ScoreKey id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ScoreStatus status;

    @OneToMany(mappedBy = "score", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<Frame> frames = new ArrayList<>();

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "player_id")
    private Player player;
}
