package com.spring.roommgmt.service.implementation;

import com.spring.roommgmt.model.BuildingTestDataBuilder;
import com.spring.roommgmt.repository.BuildingRepository;
import com.spring.roommgmt.service.BuildingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuildingServiceImplTest {

    // SUT
    private BuildingService buildingService;

    @Mock
    private BuildingRepository mockedBuildingRepository;

    @BeforeEach
    public void setUp() {
        buildingService = new BuildingServiceImpl(mockedBuildingRepository);
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
}
