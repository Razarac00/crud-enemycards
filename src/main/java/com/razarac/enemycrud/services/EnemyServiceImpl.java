package com.razarac.enemycrud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.razarac.enemycrud.entities.EEnemy;
import com.razarac.enemycrud.models.Enemy;
import com.razarac.enemycrud.models.EnemyElement;
import com.razarac.enemycrud.models.PageModel;
import com.razarac.enemycrud.repository.EnemyCrudRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class EnemyServiceImpl implements EnemyService {

    @Autowired
    private EnemyCrudRepository enemyCrudRepository;

    @Autowired
    private ElementService elementService;

    @Override
    public PageModel getEnemies(String searchName, Integer pageSize, Integer pageNumber) {
        PageModel pageModel = new PageModel();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<EEnemy> eEnemiesPage = enemyCrudRepository.findByNameContaining(searchName, pageable);
        List<EEnemy> overallEEnemies = enemyCrudRepository.findByNameContaining(searchName);
        
        List<Enemy> content = convertEEnemies(eEnemiesPage.toList());
        
        pageModel.setContent(content);
        pageModel.setPageSize(pageSize);
        pageModel.setPageNumber(pageNumber);
        pageModel.setEnemyTotal(overallEEnemies.size());
        pageModel.setEnemyOffset(pageSize * pageNumber);

        return pageModel;
    }

    @Override
    public Enemy getEnemy(String name) {
        // Expecting a full name so there SHOULD only be one
        List<EEnemy> enemies = enemyCrudRepository.findByNameContaining(name);
        if (enemies.size() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Multiple enemies named " + name);
        } else if (enemies.size() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zero enemies named " + name);
        }

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
        
        List<EnemyElement> weaknesses = eEnemy.getWeaknesses().stream().map( (element) -> elementService.getElement(element.getName()) ).collect(Collectors.toList());
        List<EnemyElement> resistances = eEnemy.getResistances().stream().map( (element) -> elementService.getElement(element.getName()) ).collect(Collectors.toList());
        List<EnemyElement> immunities = eEnemy.getImmunities().stream().map( (element) -> elementService.getElement(element.getName()) ).collect(Collectors.toList());

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
    
}
