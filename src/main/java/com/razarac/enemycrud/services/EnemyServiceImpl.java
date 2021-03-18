package com.razarac.enemycrud.services;

import com.razarac.enemycrud.models.Enemy;
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
        // TODO Auto-generated method stub
        return null;
    }
    
}
