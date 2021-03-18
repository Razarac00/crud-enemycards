package com.razarac.enemycrud.services;

import com.razarac.enemycrud.models.PageModel;

import org.springframework.stereotype.Service;

@Service
public interface EnemyService {
    PageModel getEnemies(String search, Integer pageSize, Integer pageNumber);
}
