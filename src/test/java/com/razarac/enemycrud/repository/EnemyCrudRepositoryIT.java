package com.razarac.enemycrud.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotEquals;
// import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.razarac.enemycrud.entities.EEnemy;
import com.razarac.enemycrud.entities.EEnemyElement;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EnemyCrudRepositoryIT {
    
    @Autowired
    private EnemyCrudRepository enemyCrudRepository;

    @Autowired
    private ElementCrudRepository elementCrudRepository;

    @AfterEach
    public void teardown() {
        enemyCrudRepository.deleteAllInBatch();
        elementCrudRepository.deleteAllInBatch();
    }

    @Test
    public void save_CorrectCount_WhenSaveOneEnemy() {
        // Arrange
        List<EEnemyElement> weaknesses = new ArrayList<EEnemyElement>(); 
        List<EEnemyElement> resistances = new ArrayList<EEnemyElement>(); 
        List<EEnemyElement> immunities = new ArrayList<EEnemyElement>(); 

        String name = "Burnt Ivory King";
        String image = "https://darksouls2.wiki.fextralife.com/file/Dark-Souls-2/burnt_ivory_king.png";
        String description = "Watch out for his thrust attack and magic extendo blade.";

        EEnemy enemy = new EEnemy(1L, name, weaknesses, resistances, immunities, image, description);
        
        // Act
        enemyCrudRepository.save(enemy);

        List<EEnemy> actual = enemyCrudRepository.findAll();

        // Assert
        assertEquals(1, actual.size());
        assertEquals(name, actual.get(0).getName());
    }

    @Test
    public void findByNameContaining_ReturnsListOfEnemies_OnSuccess() {
        // Arrange
        EEnemy enemy1 = EEnemy.builder().name("Burnt Ivory King").description("description").image("https://www.image.com").resistances(null).weaknesses(null).immunities(null).build();
        EEnemy enemy2 = EEnemy.builder().name("Old King Dorian").description("description").image("https://www.image.com").resistances(null).weaknesses(null).immunities(null).build();
        EEnemy enemy3 = EEnemy.builder().name("The Four Kings").description("description").image("https://www.image.com").resistances(null).weaknesses(null).immunities(null).build();
        EEnemy enemy4 = EEnemy.builder().name("The Pursuer").description("description").image("https://www.image.com").resistances(null).weaknesses(null).immunities(null).build();

        enemyCrudRepository.save(enemy1);
        enemyCrudRepository.save(enemy2);
        enemyCrudRepository.save(enemy3);
        enemyCrudRepository.save(enemy4);
        // Act
        List<EEnemy> actual = enemyCrudRepository.findByNameContaining("King");
        var unexpected = enemy4;

        // Assert
        assertEquals(3, actual.size());
        assertFalse(actual.contains(unexpected));
    }

    @Test
    public void findByNameContaining_ReturnsPageOfEnemies_OnSuccess() {
        // Arrange
        EEnemy enemy1 = EEnemy.builder().name("Burnt Ivory King").description("description").image("https://www.image.com").resistances(null).weaknesses(null).immunities(null).build();
        EEnemy enemy2 = EEnemy.builder().name("Old King Dorian").description("description").image("https://www.image.com").resistances(null).weaknesses(null).immunities(null).build();
        EEnemy enemy3 = EEnemy.builder().name("The Four Kings").description("description").image("https://www.image.com").resistances(null).weaknesses(null).immunities(null).build();
        EEnemy enemy4 = EEnemy.builder().name("The Pursuer").description("description").image("https://www.image.com").resistances(null).weaknesses(null).immunities(null).build();

        enemyCrudRepository.save(enemy1);
        enemyCrudRepository.save(enemy2);
        enemyCrudRepository.save(enemy3);
        enemyCrudRepository.save(enemy4);

        Integer pageNumber = 0;
        Integer pageSize = 2;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        // Act
        Page<EEnemy> actual = enemyCrudRepository.findByNameContaining("King", pageable);
        var unexpected = enemy4;

        // Assert
        assertEquals(pageSize, actual.getContent().size());
        assertFalse(actual.getContent().contains(unexpected));        
    }

    @Test
    public void save_CorrectElements_WhenSavingEnemy() {
        // Arrange
        EEnemyElement element1 = EEnemyElement.builder().name("None").build();
        EEnemyElement element2 = EEnemyElement.builder().name("Lightning").build();
        EEnemyElement element3 = EEnemyElement.builder().name("Fire").build();

        List<EEnemyElement> weaknesses = new ArrayList<EEnemyElement>(); 
        List<EEnemyElement> resistances = new ArrayList<EEnemyElement>(); 
        List<EEnemyElement> immunities = new ArrayList<EEnemyElement>(); 

        String name = "Burnt Ivory King";
        String image = "https://darksouls2.wiki.fextralife.com/file/Dark-Souls-2/burnt_ivory_king.png";
        String description = "Watch out for his thrust attack and magic extendo blade.";

        EEnemy enemy = EEnemy.builder().name(name).description(description).image(image).weaknesses(weaknesses).resistances(resistances).immunities(immunities).build();

        // Act
        enemy.getImmunities().add(element1);
        enemy.getWeaknesses().add(element2);
        enemy.getResistances().add(element3);

        element1.getImmuneEnemies().add(enemy);
        element2.getWeakEnemies().add(enemy);
        element3.getImmuneEnemies().add(enemy);

        var actual = enemyCrudRepository.save(enemy);

        // Assert
        assertEquals(enemy, actual);
        assertEquals(enemy.getWeaknesses(), actual.getWeaknesses());
        assertEquals(element1, actual.getImmunities().get(0));

        var received = enemyCrudRepository.getOne(actual.getId());
        assertEquals(element2, received.getWeaknesses().get(0));
        assertEquals(element3, received.getResistances().get(0));
    }

    @Test
    public void save_MultipleEnemiesWithMultipleElements_WhenCalled() {
        // Arrange
        EEnemyElement element1 = EEnemyElement.builder().name("None").build();
        EEnemyElement element2 = EEnemyElement.builder().name("Lightning").build();
        EEnemyElement element3 = EEnemyElement.builder().name("Fire").build();

        String name = "Burnt Ivory King";
        String image = "https://darksouls2.wiki.fextralife.com/file/Dark-Souls-2/burnt_ivory_king.png";
        String description = "Watch out for his thrust attack and magic extendo blade.";

        EEnemy enemy1 = EEnemy.builder().name(name).description(description).image(image).build();

        name = "Aava";
        image = "https://darksouls2.wiki.fextralife.com/file/Dark-Souls-2/aava.png";
        description = "Hope you have the priestess eye";

        EEnemy enemy2 = EEnemy.builder().name(name).description(description).image(image).build();
        // Act
        enemy1.getImmunities().add(element1);
        enemy1.getWeaknesses().add(element2);
        enemy1.getResistances().add(element3);

        element1.getImmuneEnemies().add(enemy1);
        element2.getWeakEnemies().add(enemy1);
        element3.getImmuneEnemies().add(enemy1);

        var actual1 = enemyCrudRepository.save(enemy1);

        enemy2.getImmunities().add(element1);
        enemy2.getResistances().add(element1);
        enemy2.getWeaknesses().add(element2);
        enemy2.getWeaknesses().add(element3);

        element1.getImmuneEnemies().add(enemy2);
        element1.getResistEnemies().add(enemy2);
        element2.getWeakEnemies().add(enemy2);
        element3.getWeakEnemies().add(enemy2);

        var actual2 = enemyCrudRepository.save(enemy2);

        // Assert
        assertFalse(actual1 == actual2);
        assertEquals(element1, actual1.getImmunities().get(0));
        assertEquals(element1, actual2.getResistances().get(0));
        assertTrue(actual2.getWeaknesses().contains(element2) && actual2.getWeaknesses().contains(element3));

        assertEquals(2, enemyCrudRepository.findAll().size());
    }

}
