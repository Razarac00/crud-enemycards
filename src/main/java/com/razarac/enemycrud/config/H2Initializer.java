package com.razarac.enemycrud.config;

import java.util.Collections;
import java.util.List;

import com.razarac.enemycrud.entities.*;
import com.razarac.enemycrud.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class H2Initializer implements ApplicationRunner {

    @Autowired
    private EnemyCrudRepository enemyCrudRepository;

    @Autowired
    private ElementCrudRepository elementCrudRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // TODO Auto-generated method stub
        
    }

    private void saveEnemy(EEnemy enemy) {
        elementCrudRepository.saveAll(enemy.getWeaknesses());
        elementCrudRepository.saveAll(enemy.getResistances());
        elementCrudRepository.saveAll(enemy.getImmunities());
        enemyCrudRepository.save(enemy);
    }

    private EEnemy buildEnemy(String name, List<EEnemyElement> weaknesses, List<EEnemyElement> resistances, List<EEnemyElement> immunities, String image, String description) {
        EEnemy enemy = EEnemy.builder().name(name).weaknesses(weaknesses).resistances(resistances).immunities(immunities).image(image).description(description).build();
        return enemy;
    }

    private List<EEnemyElement> buildElements(List<String> elements) {
        List<EEnemyElement> result = Collections.<EEnemyElement> emptyList();

        for (String name : elements) {
            EEnemyElement element = EEnemyElement.builder().name(name).build();
            result.add(element);
        }
        
        return result;
    }
    
}
