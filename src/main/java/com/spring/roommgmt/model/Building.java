package com.spring.roommgmt.model;


import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Building {

    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String buildingNumber;

    private String description;

    @Basic(optional = false)
    private boolean publicAccess;

}
