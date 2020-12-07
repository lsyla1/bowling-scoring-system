package com.jobsity.bowling.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "rolls")
public class Roll extends BaseEntity {

    @Column(name = "number", nullable = false)
    private int number;

    @Column(name = "pins", nullable = false)
    private int pins;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frame_id")
    private Frame frame;
}
