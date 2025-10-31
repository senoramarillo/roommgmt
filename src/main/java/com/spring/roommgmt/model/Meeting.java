package com.spring.roommgmt.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Meeting {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    private String topic;
    @Basic(optional = false)
    private Instant start;
    @Basic(optional = false)
    private Instant end;

    @ManyToOne(optional = false)
    private Room room;

    public Meeting(Long id, String topic, Instant start, Instant end) {
        this.id = id;
        this.topic = topic;
        this.start = start;
        this.end = end;
    }

}
