package com.razarac.enemycrud.entities;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "ENEMY")
@Getter @Setter
public class EEnemy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToMany @Column(name = "WEAKNESSES", nullable = false)
    private List<EEnemyElement> weaknesses;
    @ManyToMany @Column(name = "RESISTANCES", nullable = false)
    private List<EEnemyElement> resistances;
    @ManyToMany @Column(name = "IMMUNITIES", nullable = false)
    private List<EEnemyElement> immunities;
    
    @Column(name = "IMAGE", nullable = false)
    private String image;
    
    @Column(name = "DESCRIPTION", nullable = false)
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
