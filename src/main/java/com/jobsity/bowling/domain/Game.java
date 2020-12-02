package com.jobsity.bowling.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table (name = "games")
public class Game extends BaseEntity {

    @Column(name = "type", nullable = false)
    private GameType type;
}
