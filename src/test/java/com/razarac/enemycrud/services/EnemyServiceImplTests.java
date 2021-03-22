package com.razarac.enemycrud.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.razarac.enemycrud.entities.EEnemy;
import com.razarac.enemycrud.models.Enemy;
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

@ExtendWith(SpringExtension.class)
public class EnemyServiceImplTests {
    /*
    To Test:
    PageModel getEnemies(String search, Integer pageSize, Integer pageNumber);
    Enemy getEnemy(String name);
    */
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
}
