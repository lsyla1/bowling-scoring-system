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
    private String pins;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frame_id")
    private Frame frame;

    public int getPinsNumber() {
        return pins.equals("F") ? 0 : Integer.parseInt(pins);
    }
}
