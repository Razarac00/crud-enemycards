package com.razarac.enemycrud.models;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class Enemy {
    
    private Long id;

    private String name;

    private List<EnemyElement> weaknesses;
    
    private List<EnemyElement> resistances;
    
    private List<EnemyElement> immunities;
    
    private String image;
    
    private String description;

    public Enemy(Long id, String name, List<EnemyElement> weaknesses, List<EnemyElement> resistances, List<EnemyElement> immunities, String image, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;

        this.weaknesses = weaknesses;
        this.resistances = resistances;
        this.immunities = immunities;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weaknesses=" + weaknesses +
                ", resistances=" + resistances +
                ", immunities=" + immunities +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
