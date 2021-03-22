package com.razarac.enemycrud.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.razarac.enemycrud.entities.EEnemy;
import com.razarac.enemycrud.entities.EEnemyElement;
import com.razarac.enemycrud.models.Enemy;
import com.razarac.enemycrud.models.EnemyElement;
import com.razarac.enemycrud.models.PageModel;
import com.razarac.enemycrud.repository.EnemyCrudRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(SpringExtension.class)
public class EnemyServiceImplTests {

    @TestConfiguration
    static class EnemyServiceConfig {
        
        @Bean
        public EnemyService enemyService() {
            return new EnemyServiceImpl();
        }
    }

    @Autowired
    EnemyService enemyService;

    @MockBean
    EnemyCrudRepository enemyCrudRepository;

    @MockBean
    ElementService elementService;

    @BeforeEach
    public void setup() {}

    @Test
    public void getEnemies_ReturnsEmptyPageModel_WhenCalled() {
        // Arrange
        String searchName = "";
        Integer pageSize = 4;
        Integer pageNumber = 0;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<EEnemy> eEnemyList = new ArrayList<EEnemy>();
        Page<EEnemy> eEnemyPage = new PageImpl<EEnemy>(eEnemyList);
        
        Mockito.when(enemyCrudRepository.findByNameContaining(searchName, pageable)).thenReturn(eEnemyPage);
        Mockito.when(enemyCrudRepository.findByNameContaining(searchName)).thenReturn(eEnemyList);
        
        // Act
        var actual = enemyService.getEnemies(searchName, pageSize, pageNumber);
        List<Enemy> expectedList = new ArrayList<Enemy>();

        // Assert
        assertTrue(actual instanceof PageModel);
        assertEquals(expectedList, actual.getContent());
        assertEquals(pageSize, actual.getPageSize());
        assertEquals((pageSize * pageNumber), actual.getEnemyOffset());
    } 

    @Test
    public void getEnemy_ReturnsEnemy_WhenCalledByName() {
        // Arrange
        String name = "The Pursuer";
        List<EEnemyElement> eElements = new ArrayList<EEnemyElement>();
        String elementName = "None";
        eElements.add(EEnemyElement.builder().name(elementName).build());

        EEnemy enemy = EEnemy.builder().name(name).description("description").image("https://www.image.com").resistances(eElements).weaknesses(eElements).immunities(eElements).build();
        
        List<EEnemy> eEnemyList = new ArrayList<EEnemy>();
        eEnemyList.add(enemy);

        EnemyElement expectedElement = EnemyElement.builder().name(elementName).build();
        
        Mockito.when(enemyCrudRepository.findByNameContaining(name)).thenReturn(eEnemyList);
        Mockito.when(elementService.getElement(elementName)).thenReturn(expectedElement);

        // Act
        var actual = enemyService.getEnemy(name);

        // Assert
        assertTrue(actual instanceof Enemy);
        assertEquals(name, actual.getName());
    }

    @Test
    public void getEnemy_ThrowsResponseStatusException_WhenEnemyDNE() {
        // Arrange
        String name = "The Pursuer";
        List<EEnemy> eEnemyList = new ArrayList<EEnemy>();
        Mockito.when(enemyCrudRepository.findByNameContaining(name)).thenReturn(eEnemyList); 

        // Act
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> { enemyService.getEnemy(name); });

        // Assert
        assertTrue(exception.getMessage().contains("Zero"));
    }

    @Test
    public void getEnemy_ThrowsResponseStatusException_WhenMultipleEnemiesSameName() {
        // Arrange
        String name = "The Pursuer";

        List<EEnemyElement> eElements = new ArrayList<EEnemyElement>();
        String elementName = "None";
        eElements.add(EEnemyElement.builder().name(elementName).build());

        EEnemy enemy = EEnemy.builder().name(name).description("description").image("https://www.image.com").resistances(eElements).weaknesses(eElements).immunities(eElements).build();
        
        List<EEnemy> eEnemyList = new ArrayList<EEnemy>();
        eEnemyList.add(enemy);
        eEnemyList.add(enemy);

        EnemyElement expectedElement = EnemyElement.builder().name(elementName).build();

        Mockito.when(enemyCrudRepository.findByNameContaining(name)).thenReturn(eEnemyList); 
        Mockito.when(elementService.getElement(elementName)).thenReturn(expectedElement);

        // Act
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> { enemyService.getEnemy(name); });

        // Assert
        assertTrue(exception.getMessage().contains("Multiple"));
    }
}
