package com.razarac.enemycrud.services;

import java.util.List;

import com.razarac.enemycrud.entities.EEnemyElement;
import com.razarac.enemycrud.models.EnemyElement;

import org.springframework.stereotype.Service;

@Service
public interface ElementService {

    List<EnemyElement> getElements();

    EnemyElement getElement(String name);

    EEnemyElement getEElement(String name);

    EnemyElement createElement(EnemyElement enemyElement);

    EEnemyElement createEElement(String name);

    EEnemyElement createEElementNoSave(String name);

    List<EEnemyElement> createEElements(List<String> elementNames);

    EEnemyElement updateEElement(Long id, EEnemyElement eEnemyElement);

    EnemyElement deleteElementById(Long id);
}
