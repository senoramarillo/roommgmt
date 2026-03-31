package com.spring.roommgmt.model;


import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity class representing a Building.
 * Represents a building in the database with a unique building number.
 *
 * @author Spring Room Management Team
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Building {

    /**
     * Unique database identifier for the building.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Unique building number (e.g., "B001", "Building A").
     * This field is required and must be unique.
     */
    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(max = 50)
    private String buildingNumber;

    /**
     * Description of the building.
     */
    @Size(max = 255)
    private String description;

    /**
     * Indicates whether the building is publicly accessible.
     */
    @Basic(optional = false)
    private boolean publicAccess;

}
