package com.razarac.enemycrud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import static com.razarac.enemycrud.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ElementServiceImplTests {

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
    void getElements_ReturnsListOfElements_WhenCalled() {
        // Arrange
        Mockito.when(elementCrudRepository.findAll()).thenReturn(List.of(expectedEElement));

        // Act
        var actual = elementService.getElements();

        // Assert
        assertEquals(1, actual.size());
        assertEquals(expectedElement.getName(), actual.get(0).getName());
        assertEquals(expectedElement.getId(), actual.get(0).getId());
    }

    @Test
    void getElement_ReturnsElement_WhenCalledByName() {
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
    void createEElement_ReturnsCreatedElement_WhenCalledByName() {
        // Arrange
        String name = "Hollow";
        EEnemyElement element = EEnemyElement.builder().name(name).build();

        Mockito.when(elementCrudRepository.save(element)).thenReturn(element);
        
        // Act
        var actual = elementService.createEElement(name);

        // Assert
        assertSame(actual, element);
    }

    @Test
    void createEElement_ThrowsResponseStatusException_WhenNameAlreadyExists() {
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
    void createEElements_ReturnsListofCreatedElement_WhenCalledByName() {
        // Arrange
        List<String> names = List.of("Hollow", "Velstadts Helmet");
        List<EEnemyElement> elements = new ArrayList<>();
        names.forEach((n) -> {
            elements.add(EEnemyElement.builder().name(n).build());
        });

        Mockito.when(elementCrudRepository.saveAll(elements)).thenReturn(elements);
        
        // Act
        var actual = elementService.createEElements(names);

        // Assert
        assertSame(actual, elements);
    }

    @Test
    void createEElements_ThrowsResponseStatusException_WhenAnyNameAlreadyExists() {
        // Arrange
        List<String> names = List.of("Hollow", "Velstadts Helmet");
        String name = "Velstadts Helmet";
        List<EEnemyElement> elements = new ArrayList<>();
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

    @Test
    void deleteElementById_ThrowsResponseStatusException_WhenIdIsNull() {
        // Act
        // Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
           elementService.deleteElementById(null);
        });
        assertEquals(ELEMENT_IS_NULL, exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    }

    @Test
    void deleteElementById_ThrowsResponseStatusException_WhenIdIsNotFound() {
        // Arrange
        Long id = 1L;
        Mockito.when(elementCrudRepository.findById(id)).thenReturn(Optional.empty());
        String expectedReason = String.format(ELEMENT_NF_WITHID, id);
        // Act
        // Assert
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            elementService.deleteElementById(id);
        });
        assertEquals(expectedReason, exception.getReason());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void deleteElementById_ReturnsAndDeletesElement_WhenIdIsFound() {
        // Arrange
        String name = "Hollow";
        Long id = 1L;
        EEnemyElement element = EEnemyElement.builder().name(name).id(id).build();
        EnemyElement result;

        Mockito.when(elementCrudRepository.findById(id)).thenReturn(Optional.of(element));
        // Act
        result = elementService.deleteElementById(id);
        // Assert
        assertEquals(element.getName(), result.getName());
        assertEquals(element.getId(), result.getId());
        verify(elementCrudRepository, times(1)).delete(element);
    }
    
}
