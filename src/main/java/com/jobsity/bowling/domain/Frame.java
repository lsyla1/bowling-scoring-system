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
@Table(name = "frames")
public class Frame extends BaseEntity {

    @Column(name = "number", nullable = false)
    private int number;

    @OneToMany(mappedBy = "frame", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    private List<Roll> rolls = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({ @JoinColumn(name = "game_id"), @JoinColumn(name = "player_id") })
    private Score score;

    public void addRoll(Roll roll) {
        rolls.add(roll);
    }
}
