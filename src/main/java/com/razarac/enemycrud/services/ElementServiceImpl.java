package com.razarac.enemycrud.services;

import java.util.ArrayList;
import java.util.List;

import com.razarac.enemycrud.entities.*;
import com.razarac.enemycrud.models.*;
import com.razarac.enemycrud.repository.ElementCrudRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ElementServiceImpl implements ElementService {

    @Autowired
    private ElementCrudRepository elementCrudRepository;

    @Override
    public List<EnemyElement> getElements() {
        return convertEElements(elementCrudRepository.findAll());
    }

    @Override
    public EnemyElement getElement(String name) {
        return convertEElement(elementCrudRepository.findByName(name).get(0));
    }

    @Override
    public EEnemyElement createEElement(String name) {
        // Check if the element name exists, else create it
        if (elementExists(name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Element " + name + " already exists");
        }
        EEnemyElement element = buildElement(name);
        
        return elementCrudRepository.save(element);
    }

    @Override
    public List<EEnemyElement> createEElements(List<String> elementNames) {
        for (String name : elementNames) {
            if (elementExists(name)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Element " + name + " already exists");
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

    private List<EnemyElement> convertEElements(List<EEnemyElement> eElements) {
        List<EnemyElement> result = new ArrayList<EnemyElement>();

        for (EEnemyElement eElement : eElements) {
            result.add(convertEElement(eElement));
        }
        return result;
    }

    private EnemyElement convertEElement(EEnemyElement eElement) {
        EnemyElement element = EnemyElement.builder().name(eElement.getName()).id(eElement.getId()).build();
        return element;
    }
    
}
