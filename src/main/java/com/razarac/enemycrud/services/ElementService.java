package com.razarac.enemycrud.services;

import java.util.List;

import com.razarac.enemycrud.models.EnemyElement;

import org.springframework.stereotype.Service;

@Service
public interface ElementService {

    List<EnemyElement> getElements();

    EnemyElement getElement(String name);

    
    
}
