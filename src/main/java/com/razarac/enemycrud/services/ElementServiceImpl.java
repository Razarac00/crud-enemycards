package com.razarac.enemycrud.services;

import java.util.ArrayList;
import java.util.List;

import com.razarac.enemycrud.entities.*;
import com.razarac.enemycrud.models.*;
import com.razarac.enemycrud.repository.ElementCrudRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ElementServiceImpl implements ElementService {

    @Autowired
    private ElementCrudRepository elementCrudRepository;

    @Override @Transactional
    public List<EnemyElement> getElements() {
        return convertEElements(elementCrudRepository.findAll());
    }

    @Override @Transactional
    public EnemyElement getElement(String name) {
        return convertEElement(elementCrudRepository.findByName(name).get(0));
    }

    @Override @Transactional
    public EEnemyElement createEElement(String name) {
        // Check if the element name exists, else create it
        if (elementExists(name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Element " + name + " already exists");
        }
        EEnemyElement element = buildElement(name);
        
        return elementCrudRepository.save(element);
    }

    @Override @Transactional
    public List<EEnemyElement> createEElements(List<String> elementNames) {
        for (String name : elementNames) {
            if (elementExists(name)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Element " + name + " already exists");
            }            
        }
        List<EEnemyElement> elements = buildElements(elementNames);

        return elementCrudRepository.saveAll(elements);
    }

    @Override @Transactional
    public EEnemyElement createEElementNoSave(String name) {
        // Avoid saving to repo so that enemy saving will take care of it
        if (elementExists(name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Element " + name + " already exists");
        }
        EEnemyElement element = buildElement(name);
        return element;
    }

    @Override @Transactional
    public EEnemyElement getEElement(String name) {
        if (!elementExists(name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Element " + name + " does not exist");
        }
        return elementCrudRepository.findByName(name).get(0);
    }

    @Override @Transactional
    public EEnemyElement updateEElement(Long id, EEnemyElement eEnemyElement) {
        if (!elementCrudRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Element not found with id " + id);
        }
        EEnemyElement original = elementCrudRepository.getOne(id);
        String name = eEnemyElement.getName();
        List<EEnemy> weak = eEnemyElement.getWeakEnemies();
        List<EEnemy> resist = eEnemyElement.getResistEnemies();
        List<EEnemy> immune = eEnemyElement.getImmuneEnemies();

        if (!name.trim().isEmpty()) {
            original.setName(name);
        }
        if (!weak.isEmpty()) {
            for (EEnemy eEnemy : weak) {
                original.getWeakEnemies().add(eEnemy);
            }
        }
        if (!resist.isEmpty()) {
            for (EEnemy eEnemy : resist) {
                original.getResistEnemies().add(eEnemy);
            }
        }
        if (!immune.isEmpty()) {
            for (EEnemy eEnemy : immune) {
                original.getImmuneEnemies().add(eEnemy);
            }
        }
        return original;
    }

    /////////////// HELPERS ///////////////

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
