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

    @ManyToMany @Column(name = "ENEMIES")
    private List<EEnemy> enemies;

    public EEnemyElement(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public EEnemyElement(Long id, String name, List<EEnemy> enemies) {
        this.id = id;
        this.name = name;
        this.enemies = enemies;
    }
}
