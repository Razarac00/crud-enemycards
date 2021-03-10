package com.razarac.enemycrud.entities;

import java.util.List;
import java.util.Objects;

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

    @ManyToMany(cascade = CascadeType.PERSIST) 
    @Column(name = "WEAKNESSES")
    @JoinTable(
        name = "ENEMYELEMENT_WEAK",
        joinColumns = @JoinColumn(name = "ENEMY_ID"),
        inverseJoinColumns = @JoinColumn(name = "ELEMENT_ID")
    )
    private List<EEnemyElement> weaknesses;

    @ManyToMany(cascade = CascadeType.PERSIST) 
    @Column(name = "RESISTANCES")
    @JoinTable(
        name = "ENEMYELEMENT_RESIST",
        joinColumns = @JoinColumn(name = "ENEMY_ID"),
        inverseJoinColumns = @JoinColumn(name = "ELEMENT_ID")
    )
    private List<EEnemyElement> resistances;

    @ManyToMany(cascade = CascadeType.PERSIST) 
    @Column(name = "IMMUNITIES")
    @JoinTable(
        name = "ENEMYELEMENT_IMMUNE",
        joinColumns = @JoinColumn(name = "ENEMY_ID"),
        inverseJoinColumns = @JoinColumn(name = "ELEMENT_ID")
    )
    private List<EEnemyElement> immunities;
    
    @Column(name = "IMAGE", nullable = false)
    private String image;
    
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    public EEnemy() {}

    public EEnemy(Long id, String name, List<EEnemyElement> weaknesses, List<EEnemyElement> resistances, List<EEnemyElement> immunities, String image, String description) {
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
        return "Enemy id: " + getId() + " name: " + getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EEnemy)) return false;
        EEnemy enemy = (EEnemy) obj;
        return Objects.equals(getId(), enemy.id);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
