package com.jobsity.bowling.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "frames")
public class Frame extends BaseEntity {

    @Column(name = "number", nullable = false)
    private int frameNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frame")
    private List<Roll> rolls;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "game_id"),
            @JoinColumn(name = "player_id")
    })
    private Score score;
}
