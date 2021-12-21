package com.razarac.enemycrud.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class EnemyElement {
    
    private Long id;
    
    private String name;

    public EnemyElement() {}

    public EnemyElement(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "EnemyElement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
