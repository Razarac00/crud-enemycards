package com.razarac.enemycrud.mocks;

import com.razarac.enemycrud.models.Enemy;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class MockEnemy {
    public Enemy mockedEnemy() {
        String name = "Asylum Demon";
        String image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTGuSM9kU9gkhj3qTm7sRCpY7NAJ3nIRA3fkw&usqp=CAU";
        String description = "Either run through the door on the left to fight it later, or attack without locking on.";
        List<String> weak = List.of("Fire", "Bleed", "Poison", "Toxic");
        List<String> resist = List.of("None");
        List<String> immune = List.of("None");
        Enemy enemy = Enemy.builder().name(name).description(description).image(image).build();

//        enemy = buildEnemy(enemy, weak, resist, immune);

        return enemy;
    }
}
