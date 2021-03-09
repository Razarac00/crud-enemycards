package com.razarac.enemycrud.models;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EnemyElement {
    
    
    private Long id;
    
    
    private String name;

    public EnemyElement(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
