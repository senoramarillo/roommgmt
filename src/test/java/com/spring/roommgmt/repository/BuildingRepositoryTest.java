package com.spring.roommgmt.repository;

import com.spring.roommgmt.model.BuildingTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BuildingRepositoryTest {

    // SUT
    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        var building1 = new BuildingTestDataBuilder().withId(null).withBuildingNumber("Test-1").build();
        var building2 = new BuildingTestDataBuilder().withId(null).withBuildingNumber("Test-2").build();
        entityManager.persist(building1);
        entityManager.persist(building2);
    }

    @Test
    void givenOneMatchingBuilding_whenFindingByBuildingNumber_shouldReturnMatchingEntity() {
        var result = buildingRepository.findByBuildingNumber("Test-1");

        assertThat(result).isPresent();
        assertThat(result.get().getBuildingNumber()).isEqualTo("Test-1");
    }

    @Test
    void givenNoMatchingBuilding_whenFindingByBuildingNumber_shouldReturnEmptyOptional() {
        var result = buildingRepository.findByBuildingNumber("Nicht-da");

        assertThat(result).isEmpty();
    }

}
