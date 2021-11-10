package com.razarac.enemycrud.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ElementCrudControllerIT {
    @Autowired
    private MockMvc mockMvc;

    private String url;

    @BeforeEach
    void setup() {
        url = "/elements";
    }

    @Test
    void deleteElement_Returns204_OnSuccess() throws Exception {
        // Arrange
        String request = "/delete/1";

        // Act
        url = url + request;

        // Assert
        mockMvc.perform(delete(url)).andExpect(status().isNoContent());
    }

    @Test
    void deleteElement_Returns404_OnNoSuchElement() throws Exception {
        // Arrange
        String request = "/delete/-10";

        // Act
        url = url + request;

        // Assert
        mockMvc.perform(delete(url)).andExpect(status().isNotFound());
    }

    @Test
    void deleteElement_Returns400_OnNonNumber() throws Exception {
        // Arrange
        String request = "/delete/fire";

        // Act
        url = url + request;

        // Assert
        mockMvc.perform(delete(url)).andExpect(status().isBadRequest());
    }

    @Test
    void getElements_Returns200_OnSuccess() throws Exception {
        // Arrange
        String request = "/all";

        // Act
        url = url + request;

        // Assert
        mockMvc.perform(get(url)).andExpect(status().isOk());
    }
}
