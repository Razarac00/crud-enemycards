package com.razarac.enemycrud.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.razarac.enemycrud.entities.EEnemy;
import com.razarac.enemycrud.entities.EEnemyElement;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EnemyCrudRepositoryIT {
    
    @Autowired
    private EnemyCrudRepository enemyCrudRepository;

    @Autowired
    private ElementCrudRepository elementCrudRepository;

    @Test
    public void save_CorrectCount_WhenSaveOneEnemyTwoWeakOneResistTwoImmune() {
        // Arrange
        List<EEnemyElement> weaknesses = List.of(new EEnemyElement(1L, "Lightning"), new EEnemyElement(2L, "Dark"));
        List<EEnemyElement> resistances = List.of(new EEnemyElement(3L, "Fire"));
        List<EEnemyElement> immunities = List.of(new EEnemyElement(4L, "Poison"), new EEnemyElement(5L, "Toxic"));
        String name = "Burnt Ivory King";
        String image = "https://darksouls2.wiki.fextralife.com/file/Dark-Souls-2/burnt_ivory_king.png";
        String description = "Watch out for his thrust attack and magic extendo blade.";

        EEnemy enemy = new EEnemy(1L, name, weaknesses, resistances, immunities, image, description);
        // Act
        elementCrudRepository.saveAll(weaknesses);
        elementCrudRepository.saveAll(resistances);
        elementCrudRepository.saveAll(immunities);
        enemyCrudRepository.save(enemy);

        List<EEnemy> actual = enemyCrudRepository.findAll();
        List<EEnemyElement> actualElements = elementCrudRepository.findAll();
        // Assert
        assertEquals(1, actual.size());
        assertEquals(5, actualElements.size());

    }
}
