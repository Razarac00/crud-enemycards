package com.razarac.enemycrud.services;

import java.util.ArrayList;
import java.util.List;

import com.razarac.enemycrud.entities.*;
import com.razarac.enemycrud.models.*;
import com.razarac.enemycrud.repository.ElementCrudRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElementServiceImpl implements ElementService {

    @Autowired
    private ElementCrudRepository elementCrudRepository;

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

    @Override
    public EEnemyElement createEElement(String name) {
        // Check if the element name exists, else create it
        if (elementExists(name)) {
            return null;
        }
        EEnemyElement element = buildElement(name);
        
        return elementCrudRepository.save(element);
    }

    @Override
    public List<EEnemyElement> createEElements(List<String> elementNames) {
        for (String name : elementNames) {
            if (elementExists(name)) {
                return null;
            }            
        }
        List<EEnemyElement> elements = buildElements(elementNames);

        return elementCrudRepository.saveAll(elements);
    }

    private Boolean elementExists(String name) {
        return elementCrudRepository.findByName(name).size() > 0;
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
