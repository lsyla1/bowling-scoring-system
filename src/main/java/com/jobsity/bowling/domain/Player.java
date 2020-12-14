package com.jobsity.bowling.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "players")
public class Player extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
