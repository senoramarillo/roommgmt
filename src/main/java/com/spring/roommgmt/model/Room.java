package com.spring.roommgmt.model;


import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a Room.
 * Represents a room in a building with properties like seats and projector availability.
 * The combination of building and room number must be unique.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"BUILDING_ID", "ROOM_NUMBER"}))
public class Room {

    /**
     * Unique database identifier for the room.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Unique room number within its building (e.g., "101", "Room A").
     * This field is required.
     */
    @Basic(optional = false)
    @Column(name = "ROOM_NUMBER")
    @NotBlank
    private String roomNumber;

    @Basic(optional = false)
    @NotBlank
    private String floor;

    @Basic(optional = false)
    @Column(name = "MAP_X")
    @NotNull
    private Double mapX;

    @Basic(optional = false)
    @Column(name = "MAP_Y")
    @NotNull
    private Double mapY;

    /**
     * Number of seats in the room.
     * This field is required.
     */
    @Basic(optional = false)
    @NotNull
    @Min(1)
    @Max(9999)
    private Integer seats;

    /**
     * Indicates whether the room has a projector available.
     * This field is required.
     */
    @Basic(optional = false)
    private boolean projectorPresent;

    /**
     * Reference to the building that contains this room.
     * This field is required.
     */
    @ManyToOne(optional = false)
    private Building building;

    /**
     * Constructor for creating a Room with basic information.
     *
     * @param id the room ID
     * @param roomNumber the room number
     * @param seats the number of seats
     * @param projectorPresent whether a projector is present
     */
    public Room(Long id, String roomNumber, String floor, Double mapX, Double mapY, Integer seats, boolean projectorPresent) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.mapX = mapX;
        this.mapY = mapY;
        this.seats = seats;
        this.projectorPresent = projectorPresent;
    }

}
