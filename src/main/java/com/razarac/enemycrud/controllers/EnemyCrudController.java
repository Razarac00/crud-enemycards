package com.razarac.enemycrud.controllers;

import com.razarac.enemycrud.models.Enemy;
import com.razarac.enemycrud.models.PageModel;
import com.razarac.enemycrud.services.EnemyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnemyCrudController {

    @Autowired
    private EnemyService enemyService;

    @GetMapping
    @RequestMapping("/enemies")
    public PageModel getEnemies(
        @RequestParam(value = "search", required = false) String search, 
        @RequestParam("pageNumber") Integer pageNumber, 
        @RequestParam("pageSize") Integer pageSize) {
        
        if (search == null) {
            search = "";
        }
        return enemyService.getEnemies(search, pageSize, pageNumber);
    }
    
    @GetMapping("/enemies/{name}")
    public Enemy getEnemy(@PathVariable("name") String name) {

        return enemyService.getEnemy(name);
    }
}
