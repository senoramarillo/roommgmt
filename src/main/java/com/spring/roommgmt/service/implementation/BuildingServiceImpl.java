package com.spring.roommgmt.service.implementation;

import com.spring.roommgmt.model.Building;
import com.spring.roommgmt.repository.BuildingRepository;
import com.spring.roommgmt.service.BuildingService;
import com.spring.roommgmt.service.exception.DuplicateKeyException;
import com.spring.roommgmt.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;

    @Override
    public List<Building> findAll() {
        return buildingRepository.findAll();
    }

    @Override
    public Optional<Building> findByBuildingNumber(String buildingNumber) {
        return buildingRepository.findByBuildingNumber(buildingNumber);
    }

    @Override
    public List<Building> findPublic() {
        return buildingRepository.findByPublicAccessIsTrue();
    }

    @Override
    public Building createNew(Building building) {
        if (buildingRepository.findByBuildingNumber(building.getBuildingNumber()).isPresent()) {
            throw new DuplicateKeyException();
        }
        return buildingRepository.save(building);
    }

    @Override
    public Building update(String buildingNumber, Building building) {
        if (!buildingNumber.equals(building.getBuildingNumber())) {
            throw new IllegalArgumentException();
        }
        var buildingToBeUpdated = findBuildingOrThrow(buildingNumber);
        buildingToBeUpdated.setDescription(building.getDescription());
        buildingToBeUpdated.setPublicAccess(building.isPublicAccess());
        return buildingToBeUpdated;
    }

    @Override
    public void delete(String buildingNumber) {
        var buildingToBeDeleted = findBuildingOrThrow(buildingNumber);
        buildingRepository.delete(buildingToBeDeleted);
    }

    private Building findBuildingOrThrow(String buildingNumber) {
        return findByBuildingNumber(buildingNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Building not found"));
    }

}
