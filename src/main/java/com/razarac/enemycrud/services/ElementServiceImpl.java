package com.razarac.enemycrud.services;

import java.util.ArrayList;
import java.util.List;

import com.razarac.enemycrud.entities.*;
import com.razarac.enemycrud.models.*;

import org.springframework.stereotype.Component;

@Component
public class ElementServiceImpl implements ElementService {

    @Override
    public List<EnemyElement> getElements() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public EnemyElement getElement(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    private List<EEnemyElement> buildElements(List<String> elements) {
        List<EEnemyElement> result = new ArrayList<EEnemyElement>(); 

        for (String name : elements) {
            EEnemyElement element = buildElement(name);
            
            result.add(element);
        }

        return result;
    }

    private EEnemyElement buildElement(String name) {
        EEnemyElement element = EEnemyElement.builder().name(name).build(); 
        element.setWeakEnemies(new ArrayList<EEnemy>());
        element.setResistEnemies(new ArrayList<EEnemy>());
        element.setImmuneEnemies(new ArrayList<EEnemy>());

        return element;
    }
    
}
