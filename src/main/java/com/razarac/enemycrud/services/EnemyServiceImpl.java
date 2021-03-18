package com.razarac.enemycrud.services;

import java.util.ArrayList;
import java.util.List;

import com.razarac.enemycrud.entities.EEnemy;
import com.razarac.enemycrud.entities.EEnemyElement;
import com.razarac.enemycrud.models.Enemy;
import com.razarac.enemycrud.models.EnemyElement;
import com.razarac.enemycrud.models.PageModel;
import com.razarac.enemycrud.repository.EnemyCrudRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnemyServiceImpl implements EnemyService {

    @Autowired
    private EnemyCrudRepository enemyCrudRepository;

    @Override
    public PageModel getEnemies(String search, Integer pageSize, Integer pageNumber) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Enemy getEnemy(String name) {
        // Expecting a full name so there SHOULD only be one
        List<EEnemy> enemies = enemyCrudRepository.findByNameContaining(name);

        return convertEEnemy(enemies.get(0));
    }

    private List<Enemy> convertEEnemies(List<EEnemy> eEnemies) {
        List<Enemy> result = new ArrayList<Enemy>();

        for (EEnemy eEnemy : eEnemies) {
            result.add(convertEEnemy(eEnemy));
        }
        return result;
    }

    private Enemy convertEEnemy(EEnemy eEnemy) {
        List<EnemyElement> weaknesses = convertEElements(eEnemy.getWeaknesses());
        List<EnemyElement> resistances = convertEElements(eEnemy.getResistances());
        List<EnemyElement> immunities = convertEElements(eEnemy.getImmunities());

        Enemy enemy = Enemy.builder()
                        .name(eEnemy.getName())
                        .description(eEnemy.getDescription())
                        .image(eEnemy.getImage())
                        .weaknesses(weaknesses)
                        .resistances(resistances)
                        .immunities(immunities)
                        .build();
        return enemy;
    }

    private List<EnemyElement> convertEElements(List<EEnemyElement> eElements) {
        List<EnemyElement> result = new ArrayList<EnemyElement>();

        for (EEnemyElement eElement : eElements) {
            result.add(convertEElement(eElement));
        }
        return result;
    }

    private EnemyElement convertEElement(EEnemyElement eElement) {
        EnemyElement element = EnemyElement.builder().name(eElement.getName()).id(eElement.getId()).build();
        return element;
    }
    
}
