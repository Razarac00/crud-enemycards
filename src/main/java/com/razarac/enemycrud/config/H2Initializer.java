package com.razarac.enemycrud.config;

import java.util.ArrayList;
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
        List<String> elementNames = new ArrayList<String>();
        elementNames.addAll(List.of("Magic", "Fire", "Lightning", "None", "Dark", "Poison", "Toxic", "Standard", "Strike", "Thrust", "Slash"));

        // Add special weapons/items/cheese stuff here
        var specialNames = new ArrayList<String>();
        specialNames.addAll(List.of("Ledge", "Backstab", "Parry", "Bracelets", "Farron Greatsword"));

        elementNames.addAll(specialNames);

        List<EEnemyElement> elements = buildElements(elementNames);
        elementCrudRepository.saveAll(elements);
        
        // ------------------ BOSS ATTRIBUTES ------------------- //
        String name = "";
        String image = "";
        String description = "";
        List<String> weak = Collections.<String> emptyList();
        List<String> resist = Collections.<String> emptyList();
        List<String> immune = Collections.<String> emptyList();
        EEnemy enemy;

        // ------------------------------------------------------ //
        // ------------------- DARK SOULS ONE ------------------- //
        // ------------------------------------------------------ //
        /* ASYLUM DEMON */
        name = "Asylum Demon";
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGuSM9kU9gkhj3qTm7sRCpY7NAJ3nIRA3fkw&usqp=CAU";
        description = "Either run through the door on the left to fight it later, or attack without locking on.";
        weak = List.of("Fire");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

        /* BELL GARGOYLE */
        name = "Bell Gargoyle";
        image = "https://i.pinimg.com/originals/7e/f5/04/7ef5043586728dc2bdf39d957bf84760.jpg";
        description = "There are two. Cut off the tail of one of them.";
        weak = List.of("Fire, Lightning");
        resist = List.of("None");
        immune = List.of("None");
        enemy = EEnemy.builder().name(name).description(description).image(image).build();

        saveEnemy(buildEnemy(enemy, weak, resist, immune));

    }

    private void saveEnemy(EEnemy enemy) {
        for (EEnemyElement element : enemy.getWeaknesses()) {
            element.getWeakEnemies().add(enemy);
        }
        for (EEnemyElement element : enemy.getResistances()) {
            element.getResistEnemies().add(enemy);
        }
        for (EEnemyElement element : enemy.getImmunities()) {
            element.getImmuneEnemies().add(enemy);
        }
        enemyCrudRepository.save(enemy);
    }

    private EEnemy buildEnemy(EEnemy enemy, List<String> weak, List<String> resist, List<String> immune) {
        List<EEnemyElement> result = new ArrayList<EEnemyElement>();

        for (String elementName : weak) {
            result.addAll(elementCrudRepository.findByName(elementName));
            enemy.setWeaknesses(result);
            result.clear();
        }
        for (String elementName : resist) {
            result.addAll(elementCrudRepository.findByName(elementName));
            enemy.setResistances(result);
            result.clear();
        }
        for (String elementName : immune) {
            result.addAll(elementCrudRepository.findByName(elementName));
            enemy.setImmunities(result);
            result.clear();
        }

        return enemy;
    }

    private List<EEnemyElement> buildElements(List<String> elements) {
        List<EEnemyElement> result = new ArrayList<EEnemyElement>(); 

        for (String name : elements) {
            EEnemyElement element = EEnemyElement.builder().name(name).build(); 
            element.setWeakEnemies(new ArrayList<EEnemy>());
            element.setResistEnemies(new ArrayList<EEnemy>());
            element.setImmuneEnemies(new ArrayList<EEnemy>());
            
            result.add(element);
        }

        return result;
    }
    
}
