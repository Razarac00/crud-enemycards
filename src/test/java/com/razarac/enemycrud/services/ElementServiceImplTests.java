package com.razarac.enemycrud.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(SpringExtension.class)
public class ElementServiceImplTests {

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

    @Test
    public void createEElement_ReturnsCreatedElement_WhenCalledByName() {
        // Arrange
        String name = "Hollow";
        EEnemyElement element = EEnemyElement.builder().name(name).build();

        Mockito.when(elementCrudRepository.save(element)).thenReturn(element);
        
        // Act
        var actual = elementService.createEElement(name);

        // Assert
        assertTrue(actual == element);
    }

    @Test
    public void createEElement_ThrowsResponseStatusException_WhenNameAlreadyExists() {
        // Arrange
        String name = "Hollow";
        EEnemyElement element = EEnemyElement.builder().name(name).build();

        Mockito.when(elementCrudRepository.save(element)).thenReturn(element);
        Mockito.when(elementCrudRepository.findByName(name)).thenReturn(List.of(element));
        
        // Act
        // Assert
        assertThrows(ResponseStatusException.class, () -> {
            elementService.createEElement(name);
        });
    }

    @Test
    public void createEElements_ReturnsListofCreatedElement_WhenCalledByName() {
        // Arrange
        List<String> names = List.of("Hollow", "Velstadts Helmet");
        List<EEnemyElement> elements = new ArrayList<EEnemyElement>(); 
        names.forEach((n) -> {
            elements.add(EEnemyElement.builder().name(n).build());
        });

        Mockito.when(elementCrudRepository.saveAll(elements)).thenReturn(elements);
        
        // Act
        var actual = elementService.createEElements(names);

        // Assert
        assertTrue(actual == elements);
    }

    @Test
    public void createEElements_ThrowsResponseStatusException_WhenAnyNameAlreadyExists() {
        // Arrange
        List<String> names = List.of("Hollow", "Velstadts Helmet");
        String name = "Velstadts Helmet";
        List<EEnemyElement> elements = new ArrayList<EEnemyElement>(); 
        names.forEach((n) -> {
            elements.add(EEnemyElement.builder().name(n).build());
        });
        EEnemyElement element = elements.get(1);
        

        Mockito.when(elementCrudRepository.saveAll(elements)).thenReturn(elements);
        Mockito.when(elementCrudRepository.findByName(name)).thenReturn(List.of(element));
        
        // Act
        // Assert
        assertThrows(ResponseStatusException.class, () -> {
            elementService.createEElements(names);
        });
    }
    
}
