package com.razarac.enemycrud.services;

import com.razarac.enemycrud.models.*;

import org.springframework.stereotype.Service;

@Service
public interface EnemyService {
    
    PageModel getEnemies(String search, Integer pageSize, Integer pageNumber);

    Enemy getEnemy(String name);
}
