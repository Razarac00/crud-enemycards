package com.razarac.enemycrud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.razarac.enemycrud.entities.EEnemy;
import com.razarac.enemycrud.entities.EEnemyElement;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Component
public class EnemyServiceImpl implements EnemyService {

    @Autowired
    private EnemyCrudRepository enemyCrudRepository;

    @Autowired
    private ElementService elementService;

    @Override @Transactional
    public PageModel getEnemies(String searchName, Integer pageSize, Integer pageNumber) {
        PageModel pageModel = new PageModel();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<EEnemy> eEnemiesPage = enemyCrudRepository.findByNameContaining(searchName, pageable);
        
        List<Enemy> content = convertEEnemies(eEnemiesPage.toList());
        
        Long totalEnemies = eEnemiesPage.getTotalElements();
        
        pageModel.setContent(content);
        pageModel.setPageSize(pageSize);
        pageModel.setPageNumber(pageNumber);
        pageModel.setPageTotal(eEnemiesPage.getTotalPages());
        pageModel.setEnemyTotal(totalEnemies.intValue());
        pageModel.setEnemyOffset(pageSize * pageNumber);
        pageModel.setHasNext(eEnemiesPage.hasNext());
        pageModel.setHasPrevious(eEnemiesPage.hasPrevious());

        return pageModel;
    }

    @Override @Transactional
    public Enemy getEnemy(String name) {
        // Expecting a full name so there SHOULD only be one
        name = name.replace("-", " ");
        List<EEnemy> enemies = enemyCrudRepository.findByNameContaining(name);
        if (enemies.size() > 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Multiple enemies named " + name);
        } else if (enemies.size() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zero enemies named " + name);
        }

        return convertEEnemy(enemies.get(0));
    }

    @Override @Transactional
    public Enemy addEnemy(Enemy enemy) {
        String name = enemy.getName();

        if (enemyExists(name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Enemy already exists named " + name);
        }

        String desc = enemy.getDescription();
        String image = enemy.getImage();
        List<EnemyElement> weak = enemy.getWeaknesses();
        List<EnemyElement> resist = enemy.getResistances();
        List<EnemyElement> immune = enemy.getImmunities();

        EEnemy newEnemy = EEnemy.builder().name(name).description(desc).image(image).build();
        newEnemy = setupElements(newEnemy, weak, resist, immune);

        enemyCrudRepository.save(newEnemy);
        return convertEEnemy(newEnemy);
    }

    @Override
    public Enemy addEnemy(EEnemy eEnemy) {
        Enemy enemy = convertEEnemy(eEnemy);

        return addEnemy(enemy);
    }

    @Override @Transactional
    public Enemy updateEnemy(Long id, Enemy enemy) {
        if (!enemyCrudRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Enemy does not exist with id " + id);
        }
        EEnemy original = enemyCrudRepository.getOne(id);

        String name = enemy.getName();
        String desc = enemy.getDescription();
        String image = enemy.getImage();
        List<EnemyElement> weak = enemy.getWeaknesses();
        List<EnemyElement> resist = enemy.getResistances();
        List<EnemyElement> immune = enemy.getImmunities();

        if (!name.trim().isEmpty()) {
            original.setName(name);
        }
        if (!desc.trim().isEmpty()) {
            original.setDescription(desc);
        }
        if (!image.trim().isEmpty()) {
            original.setImage(image);
        }
        original = setupElements(original, weak, resist, immune);

        enemyCrudRepository.save(original);

        return convertEEnemy(original);
    }

    /////////////// HELPERS ///////////////

    private EEnemy setupElements(EEnemy src, List<EnemyElement> weak, List<EnemyElement> resist, List<EnemyElement> immune) {
        if (!weak.isEmpty()) {
            for (EnemyElement enemyElement : weak) {
                try {
                    EEnemyElement eElement = elementService.getEElement(enemyElement.getName());
                    eElement.getWeakEnemies().add(src);
                } catch (ResponseStatusException e) {
                    EEnemyElement eElement = elementService.createEElementNoSave(enemyElement.getName());
                    eElement.getWeakEnemies().add(src);
                }
            }

        }
        if (!resist.isEmpty()) {
            for (EnemyElement enemyElement : resist) {
                try {
                    EEnemyElement eElement = elementService.getEElement(enemyElement.getName());
                    eElement.getResistEnemies().add(src);
                } catch (ResponseStatusException e) {
                    EEnemyElement eElement = elementService.createEElementNoSave(enemyElement.getName());
                    eElement.getResistEnemies().add(src);
                }
            }
        }
        if (!immune.isEmpty()) {
            for (EnemyElement enemyElement : immune) {
                try {
                    EEnemyElement eElement = elementService.getEElement(enemyElement.getName());
                    eElement.getImmuneEnemies().add(src);
                } catch (ResponseStatusException e) {
                    EEnemyElement eElement = elementService.createEElementNoSave(enemyElement.getName());
                    eElement.getImmuneEnemies().add(src);
                }
            }
        }
        return src;
    }

    private Boolean enemyExists(String name) {
        return enemyCrudRepository.findByNameContaining(name).size() >= 1;
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
