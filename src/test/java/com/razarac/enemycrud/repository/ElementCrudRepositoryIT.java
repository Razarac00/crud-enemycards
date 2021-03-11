package com.razarac.enemycrud.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import com.razarac.enemycrud.entities.EEnemyElement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ElementCrudRepositoryIT {
    @Autowired
    private ElementCrudRepository elementCrudRepository;

    @AfterEach
    public void teardown() {
        elementCrudRepository.deleteAll();
    }

    @Test
    public void save_ReturnsEntity_OnSuccess() {
        // Arrange
        EEnemyElement expected = EEnemyElement.builder().name("Fire").build();
        // Act
        elementCrudRepository.save(expected);
        
        EEnemyElement actual = elementCrudRepository.getOne(expected.getId());

        // Assert
        assertEquals(expected, actual);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void build_ReturnsNullId_WhenDuplicateIsCreated() {
        // Arrange
        EEnemyElement expected = EEnemyElement.builder().name("Fire").build();
        // Act
        elementCrudRepository.save(expected);
        EEnemyElement duplicate = EEnemyElement.builder().name("Fire").build();

        // Assert
        assertNull(duplicate.getId());
    }

    @Test
    public void findByName_ReturnsListOfOneMatchingElement_WhenCalled() {
        // Arrange
        List<EEnemyElement> elements = List.of(EEnemyElement.builder().name("Fire").build(), 
                                            EEnemyElement.builder().name("Lightning").build(),
                                            EEnemyElement.builder().name("Light").build(),
                                            EEnemyElement.builder().name("Dark").build());

        List<EEnemyElement> expected = List.of(elements.get(2));
        // Act
        elementCrudRepository.saveAll(elements);
        List<EEnemyElement> actual = elementCrudRepository.findByName("Light");

        // Assert
        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());

    }
}
