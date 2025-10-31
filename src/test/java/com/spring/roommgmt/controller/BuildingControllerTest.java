package com.spring.roommgmt.controller;

import com.spring.roommgmt.model.BuildingTestDataBuilder;
import com.spring.roommgmt.service.BuildingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BuildingController.class)
class BuildingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildingService mockedBuildingService;

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