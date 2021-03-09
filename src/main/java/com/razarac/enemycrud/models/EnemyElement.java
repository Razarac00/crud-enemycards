package com.razarac.enemycrud.models;

import java.util.List;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class EnemyElement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;

    @ManyToMany
    private List<Enemy> enemies;

    public EnemyElement(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public EnemyElement(Long id, String name, List<Enemy> enemies) {
        this.id = id;
        this.name = name;
        this.enemies = enemies;
    }
}
