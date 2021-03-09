package com.razarac.enemycrud.models;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Enemy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany @Column(nullable = false)
    private List<EnemyElement> weaknesses;
    @ManyToMany @Column(nullable = false)
    private List<EnemyElement> resistances;
    @ManyToMany @Column(nullable = false)
    private List<EnemyElement> immunities;
    
    @Column(nullable = false)
    private String image;
    
    @Column(nullable = false)
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
}
