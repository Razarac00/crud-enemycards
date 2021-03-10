package com.razarac.enemycrud.entities;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class EEnemy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany @Column(nullable = false)
    private List<EEnemyElement> weaknesses;
    @ManyToMany @Column(nullable = false)
    private List<EEnemyElement> resistances;
    @ManyToMany @Column(nullable = false)
    private List<EEnemyElement> immunities;
    
    @Column(nullable = false)
    private String image;
    
    @Column(nullable = false)
    private String description;

    public EEnemy(Long id, String name, List<EEnemyElement> weaknesses, List<EEnemyElement> resistances, List<EEnemyElement> immunities, String image, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;

        this.weaknesses = weaknesses;
        this.resistances = resistances;
        this.immunities = immunities;
    }
}
