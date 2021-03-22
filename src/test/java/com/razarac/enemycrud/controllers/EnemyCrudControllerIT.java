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
    public void getEnemies_Return200Status_WhenCalled() throws Exception {
        // Arrange
        String request = "?search=" + search + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;

        // Act
        url = url + request;
        
        // Assert
        mockMvc.perform(get(url)).andExpect(status().isOk());
    }

    @Test
    public void getEnemies_Return200Status_WhenCalledWithoutSearch() throws Exception {
        // Arrange
        String request = "?pageNumber=" + pageNumber + "&pageSize=" + pageSize;

        // Act
        url = url + request;
        
        // Assert
        mockMvc.perform(get(url)).andExpect(status().isOk());
    }
}
