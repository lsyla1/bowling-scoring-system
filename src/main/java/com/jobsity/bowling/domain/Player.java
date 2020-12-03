package com.jobsity.bowling.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "players")
public class Player extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
