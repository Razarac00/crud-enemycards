package com.razarac.enemycrud.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import com.razarac.enemycrud.entities.EEnemyElement;
import com.razarac.enemycrud.models.EnemyElement;
import com.razarac.enemycrud.repository.ElementCrudRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ElementServiceImplTests {
    /*
    To Test:
    List<EnemyElement> getElements();

    EnemyElement getElement(String name);

    EEnemyElement createEElement(String name);

    List<EEnemyElement> createEElements(List<String> elementNames);
    */

    @TestConfiguration
    static class ElementServiceTestConfig {

        @Bean
        public ElementService elementService() {
            return new ElementServiceImpl();
        }
    }

    @Autowired
    ElementService elementService;

    @MockBean
    ElementCrudRepository elementCrudRepository;

    private EnemyElement expectedElement;
    private EEnemyElement expectedEElement;

    @BeforeEach
    public void setup() {
        String expectedName = "Lifehunt Scythe";
        Long expectedId = 1L;
        expectedElement = EnemyElement.builder().name(expectedName).id(expectedId).build();
        expectedEElement = EEnemyElement.builder().name(expectedName).id(expectedId).build();
    }

    @Test
    public void getElements_ReturnsListOfElements_WhenCalled() {
        // Arrange
        Mockito.when(elementCrudRepository.findAll()).thenReturn(List.of(expectedEElement));

        // Act
        var actual = elementService.getElements();

        // Assert
        assertTrue(actual.size() == 1);
        assertEquals(expectedElement.getName(), actual.get(0).getName());
        assertEquals(expectedElement.getId(), actual.get(0).getId());
    }

    @Test
    public void getElement_ReturnsElement_WhenCalledByName() {
        // Arrange
        String name = "Lifehunt Scythe";
        Mockito.when(elementCrudRepository.findByName(name)).thenReturn(List.of(expectedEElement));

        // Act
        var actual = elementService.getElement(name);

        // Assert
        assertEquals(expectedElement.getName(), actual.getName());
        assertEquals(expectedElement.getId(), actual.getId());
    }
    
}
