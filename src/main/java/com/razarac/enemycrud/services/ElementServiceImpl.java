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

import static com.razarac.enemycrud.utils.Constants.*;

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
        if (Boolean.TRUE.equals(elementExists(name))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(ELEMENT_BR_EXISTS, name));
        }
        EEnemyElement element = buildElement(name);
        
        return elementCrudRepository.save(element);
    }

    @Override @Transactional
    public List<EEnemyElement> createEElements(List<String> elementNames) {
        for (String name : elementNames) {
            if (Boolean.TRUE.equals(elementExists(name))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(ELEMENT_BR_EXISTS, name));
            }            
        }
        List<EEnemyElement> elements = buildElements(elementNames);

        return elementCrudRepository.saveAll(elements);
    }

    @Override @Transactional
    public EEnemyElement createEElementNoSave(String name) {
        // Avoid saving to repo so that enemy saving will take care of it
        if (Boolean.TRUE.equals(elementExists(name))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(ELEMENT_BR_EXISTS, name));
        }
        return buildElement(name);
    }

    @Override @Transactional
    public EEnemyElement getEElement(String name) {
        if (Boolean.FALSE.equals(elementExists(name))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format(ELEMENT_BR_NOTEXIST, name));
        }
        return elementCrudRepository.findByName(name).get(0);
    }

    @Override @Transactional
    public EEnemyElement updateEElement(Long id, EEnemyElement eEnemyElement) {
        if (!elementCrudRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(ELEMENT_NF_WITHID, id));
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

    @Override @Transactional
    public EnemyElement deleteElementById(Long id) {
        if (!elementCrudRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format(ELEMENT_NF_WITHID, id));
        }
        EnemyElement element = convertEElement(elementCrudRepository.findById(id).get());
        elementCrudRepository.deleteById(id);
        return element;
    }

    /////////////// HELPERS ///////////////

    private Boolean elementExists(String name) {
        return !elementCrudRepository.findByName(name).isEmpty();
    }

    private List<EEnemyElement> buildElements(List<String> elements) {
        List<EEnemyElement> result = new ArrayList<>();

        for (String name : elements) {
            EEnemyElement element = buildElement(name);
            
            result.add(element);
        }

        return result;
    }

    private EEnemyElement buildElement(String name) {
        return EEnemyElement.builder().name(name).build();
    }

    private List<EnemyElement> convertEElements(List<EEnemyElement> eElements) {
        List<EnemyElement> result = new ArrayList<>();

        for (EEnemyElement eElement : eElements) {
            result.add(convertEElement(eElement));
        }
        return result;
    }

    private EnemyElement convertEElement(EEnemyElement eElement) {
        return EnemyElement.builder().name(eElement.getName()).id(eElement.getId()).build();
    }
    
}
