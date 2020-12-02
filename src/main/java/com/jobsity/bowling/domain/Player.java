package com.jobsity.bowling.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "players")
public class Player extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
