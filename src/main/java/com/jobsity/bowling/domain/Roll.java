package com.jobsity.bowling.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "rolls")
public class Roll extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "frame_id")
    private Frame frame;

    @Column(name = "number", nullable = false)
    private int rollNumber;

    @Column(name = "pinfalls", nullable = false)
    private int pinfalls;
}
