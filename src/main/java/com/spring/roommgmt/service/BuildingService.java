package com.spring.roommgmt.service;

import com.spring.roommgmt.model.Building;

import java.util.List;
import java.util.Optional;

public interface BuildingService {

    List<Building> findAll();

    Optional<Building> findByBuildingNumber(String buildingNumber);

    List<Building> findPublic();

    Building createNew(Building building);

    Building update(String buildingNumber, Building building);

    void delete(String buildingNumber);

}

