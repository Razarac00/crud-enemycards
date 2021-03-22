package com.razarac.enemycrud.controllers;

import com.razarac.enemycrud.models.Enemy;
import com.razarac.enemycrud.models.PageModel;
import com.razarac.enemycrud.services.EnemyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnemyCrudController {

    @Autowired
    private EnemyService enemyService;

    @GetMapping
    @RequestMapping("/enemies")
    public PageModel getEnemies(String searchName, Integer pageNumber, Integer pageSize) {

        return enemyService.getEnemies(searchName, pageSize, pageNumber);
    }
    
    @GetMapping
    @RequestMapping("/enemy")
    public Enemy getEnemy(String searchName) {

        return enemyService.getEnemy(searchName);
    }
}
