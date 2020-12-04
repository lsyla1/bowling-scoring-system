package com.jobsity.bowling.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "players")
public class Player extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
