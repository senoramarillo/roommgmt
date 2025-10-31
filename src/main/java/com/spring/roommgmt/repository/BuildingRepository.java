package com.spring.roommgmt.repository;

import com.spring.roommgmt.model.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Long> {

    Optional<Building> findByBuildingNumber(String buildingNumber);

    List<Building> findByPublicAccessIsTrue();

}