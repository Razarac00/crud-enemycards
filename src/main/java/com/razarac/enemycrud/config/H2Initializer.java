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
        // ------------------------------------------------------ //
        // ---------------- DARK SOULS ELEMENTS ----------------- //
        // ------------------------------------------------------ //
        List<String> elementNames = List.of("Magic", "Fire", "Lightning", "None", "Dark", "Poison", "Toxic", "Standard", "Strike", "Thrust", "Slash");
        // Add special weapons for ds2/ds3 stuff here
        // elementNames.addAll(List.of("", ""));
        List<EEnemyElement> elements = buildElements(elementNames);
        elementCrudRepository.saveAll(elements);
        
    }

    private void saveEnemy(EEnemy enemy) {
        elementCrudRepository.saveAll(enemy.getWeaknesses());
        elementCrudRepository.saveAll(enemy.getResistances());
        elementCrudRepository.saveAll(enemy.getImmunities());
        enemyCrudRepository.save(enemy);
    }

    private EEnemy buildEnemy(EEnemy enemy, List<String> weak, List<String> resist, List<String> immune) {
        for (String elementName : immune) {
            elementCrudRepository.findByName(elementName);
        }
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
