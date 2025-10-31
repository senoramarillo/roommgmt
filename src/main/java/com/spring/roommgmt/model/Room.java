package com.spring.roommgmt.model;


import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"BUILDING_ID", "ROOM_NUMBER"}))
public class Room {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    @Column(name = "ROOM_NUMBER")
    private String roomNumber;

    @Basic(optional = false)
    private Integer seats;

    @Basic(optional = false)
    private boolean projectorPresent;

    @ManyToOne(optional = false)
    private Building building;

    public Room(Long id, String roomNumber, Integer seats, boolean projectorPresent) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.seats = seats;
        this.projectorPresent = projectorPresent;
    }

}
