package com.spring.roommgmt.controller;

import com.spring.roommgmt.model.BuildingTestDataBuilder;
import com.spring.roommgmt.service.BuildingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BuildingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BuildingService mockedBuildingService;

    @InjectMocks
    private BuildingController buildingController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(buildingController).build();
    }

    @Test
    void whenFindAll_shouldReturnBuildingList() throws Exception {
        when(mockedBuildingService.findAll()).thenReturn(List.of(
                new BuildingTestDataBuilder().withId(1L).withBuildingNumber("Test 1").build(),
                new BuildingTestDataBuilder().withId(2L).withBuildingNumber("Test 2").build()
        ));

        mockMvc.perform(get("/buildings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].buildingNumber").value("Test 1"))
                .andExpect(jsonPath("$[1].id").value("2"))
                .andExpect(jsonPath("$[1].buildingNumber").value("Test 2"));
    }

}