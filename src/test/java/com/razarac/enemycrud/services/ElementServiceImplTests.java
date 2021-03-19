package com.razarac.enemycrud.services;

import com.razarac.enemycrud.repository.ElementCrudRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @BeforeEach
    public void setup() {}
    
}
