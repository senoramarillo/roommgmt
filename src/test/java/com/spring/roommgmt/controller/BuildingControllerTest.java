package com.spring.roommgmt.controller;

import com.spring.roommgmt.model.BuildingTestDataBuilder;
import com.spring.roommgmt.service.BuildingService;
import com.spring.roommgmt.service.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc = MockMvcBuilders.standaloneSetup(buildingController)
                .setControllerAdvice(new ExceptionController())
                .build();
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

    @Test
    void whenFindByBuildingNumber_shouldReturnBuilding() throws Exception {
        when(mockedBuildingService.findByBuildingNumber("B-101"))
                .thenReturn(Optional.of(new BuildingTestDataBuilder().withId(1L).withBuildingNumber("B-101").build()));

        mockMvc.perform(get("/buildings/number/B-101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.buildingNumber").value("B-101"));
    }

    @Test
    void whenCreateBuildingWithInvalidPayload_shouldReturnBadRequestWithValidationErrors() throws Exception {
        mockMvc.perform(post("/buildings")
                        .contentType(APPLICATION_JSON)
                        .content("""
                                {
                                  "buildingNumber": "",
                                  "description": "HQ",
                                  "publicAccess": true
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Request validation failed"))
                .andExpect(jsonPath("$.validationErrors.buildingNumber").exists());
    }

    @Test
    void whenBuildingNotFound_shouldReturnStructuredNotFoundError() throws Exception {
        when(mockedBuildingService.findByBuildingNumber("missing"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/buildings/number/missing"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Building not found"))
                .andExpect(jsonPath("$.path").value("/buildings/number/missing"));
    }
}
