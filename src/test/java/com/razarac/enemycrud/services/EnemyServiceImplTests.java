package com.razarac.enemycrud.services;

import com.razarac.enemycrud.repository.EnemyCrudRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
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
}
