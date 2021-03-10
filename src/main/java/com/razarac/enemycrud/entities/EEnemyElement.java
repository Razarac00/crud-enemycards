package com.razarac.enemycrud.entities;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "ENEMYELEMENT")
@Getter @Setter
public class EEnemyElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "weaknesses") 
    @Column(name = "ENEMIES")
    private List<EEnemy> weakEnemies;

    @ManyToMany(mappedBy = "resistances") 
    @Column(name = "ENEMIES")
    private List<EEnemy> resistEnemies;   

    @ManyToMany(mappedBy = "weaknesses") 
    @Column(name = "ENEMIES")
    private List<EEnemy> immuneEnemies;

    public EEnemyElement(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public EEnemyElement(Long id, String name, List<EEnemy> weakEnemies, List<EEnemy> resistEnemies, List<EEnemy> immuneEnemies) {
        this.id = id;
        this.name = name;
        this.weakEnemies = weakEnemies;
        this.resistEnemies = resistEnemies;
        this.immuneEnemies = immuneEnemies;
    }
}
