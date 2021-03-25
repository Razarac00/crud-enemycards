package com.razarac.enemycrud.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc // used autoconfig over mockmvc since mockmvc only tests controller w/o dependents
public class EnemyCrudControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private String search;
    private Integer pageNumber;
    private Integer pageSize;
    private String url;

    @BeforeEach
    public void setup() {
        search = "K";
        pageNumber = 0;
        pageSize = 2;
        url = "/enemies";
    }

    @Test
    public void getEnemies_Returns200Status_WhenCalled() throws Exception {
        // Arrange
        String request = "?search=" + search + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;

        // Act
        url = url + request;
        
        // Assert
        mockMvc.perform(get(url)).andExpect(status().isOk());
    }

    @Test
    public void getEnemies_Returns200Status_WhenCalledWithoutSearch() throws Exception {
        // Arrange
        String request = "?pageNumber=" + pageNumber + "&pageSize=" + pageSize;

        // Act
        url = url + request;
        
        // Assert
        mockMvc.perform(get(url)).andExpect(status().isOk());
    }

    @Test
    public void getEnemy_Returns200Status_WhenCalledByName() throws Exception {
        // Arrange
        String request = "/Nito";

        // Act
        url = url + request;

        // Assert
        MvcResult result = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actual = result.getResponse().getContentAsString();
        assertTrue(actual.contains("Nito"));
    }

    @Test
    public void getEnemy_Returns200Status_WhenCalledByNamesWithDashes() throws Exception {
        // Arrange
        String request = "/Gwyn-Lord-of-Cinder";

        // Act
        url = url + request;

        // Assert
        MvcResult result = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actual = result.getResponse().getContentAsString();
        assertTrue(actual.contains("Gwyn Lord of Cinder"));
    }

    @Test
    public void getEnemy_ContainsElementInfo_WhenCalledByName() throws Exception {
        // Arrange
        String request = "/Nito";

        // Act
        url = url + request;

        // Assert
        MvcResult result = mockMvc.perform(get(url)).andExpect(status().isOk()).andReturn();
        String actual = result.getResponse().getContentAsString();
        assertTrue(actual.contains("Nito"));
        assertTrue(actual.contains("Fire"));
        assertTrue(actual.contains("None"));
        assertTrue(actual.contains("Poison"));
        assertTrue(actual.contains("Toxic"));
    }
    
}
