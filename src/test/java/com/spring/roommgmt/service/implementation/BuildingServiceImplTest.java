package com.spring.roommgmt.service.implementation;

import com.spring.roommgmt.model.BuildingTestDataBuilder;
import com.spring.roommgmt.repository.BuildingRepository;
import com.spring.roommgmt.repository.RoomRepository;
import com.spring.roommgmt.service.BuildingService;
import com.spring.roommgmt.service.exception.DuplicateKeyException;
import com.spring.roommgmt.service.exception.ResourceConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuildingServiceImplTest {

    private BuildingService buildingService;

    @Mock
    private BuildingRepository mockedBuildingRepository;

    @Mock
    private RoomRepository mockedRoomRepository;

    @BeforeEach
    public void setUp() {
        buildingService = new BuildingServiceImpl(mockedBuildingRepository, mockedRoomRepository);
    }

    @Test
    void whenFindAll_shouldCallRepository() {
        when(mockedBuildingRepository.findAll()).thenReturn(of(
                new BuildingTestDataBuilder().build(),
                new BuildingTestDataBuilder().build()
        ));

        var result = buildingService.findAll();

        assertThat(result).hasSize(2);
        verify(mockedBuildingRepository).findAll();
    }

    @Test
    void whenCreateNewWithDuplicateBuildingNumber_shouldThrowConflict() {
        var building = new BuildingTestDataBuilder().withBuildingNumber("B-101").build();
        when(mockedBuildingRepository.findByBuildingNumber("B-101")).thenReturn(Optional.of(building));

        assertThatThrownBy(() -> buildingService.createNew(building))
                .isInstanceOf(DuplicateKeyException.class)
                .hasMessage("A building with this building number already exists");
    }

    @Test
    void whenDeleteWithRoomsAssigned_shouldThrowConflict() {
        var building = new BuildingTestDataBuilder().withBuildingNumber("B-101").build();
        when(mockedBuildingRepository.findByBuildingNumber("B-101")).thenReturn(Optional.of(building));
        when(mockedRoomRepository.existsByBuilding(building)).thenReturn(true);

        assertThatThrownBy(() -> buildingService.delete("B-101"))
                .isInstanceOf(ResourceConflictException.class)
                .hasMessage("Building cannot be deleted while rooms still belong to it");
    }
}
