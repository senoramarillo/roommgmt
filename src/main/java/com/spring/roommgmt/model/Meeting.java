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

/**
 * Entity class representing a Meeting.
 * Represents a meeting scheduled in a specific room with start and end times.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Meeting {

    /**
     * Unique database identifier for the meeting.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Topic or title of the meeting.
     * This field is required.
     */
    @Basic(optional = false)
    private String topic;

    /**
     * Start time of the meeting.
     * This field is required.
     */
    @Basic(optional = false)
    private Instant start;

    /**
     * End time of the meeting.
     * This field is required.
     */
    @Basic(optional = false)
    private Instant end;

    /**
     * Reference to the room where the meeting is held.
     * This field is required.
     */
    @ManyToOne(optional = false)
    private Room room;

    /**
     * Constructor for creating a Meeting with basic information.
     *
     * @param id the meeting ID
     * @param topic the meeting topic
     * @param start the start time
     * @param end the end time
     */
    public Meeting(Long id, String topic, Instant start, Instant end) {
        this.id = id;
        this.topic = topic;
        this.start = start;
        this.end = end;
    }

}
