package com.razarac.enemycrud.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity 
@Table(name = "ENEMYELEMENT")
@Getter @Setter @Builder
public class EEnemyElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        }) 
    @Column(name = "WEAKENEMIES")
    @JoinTable(
        name = "ENEMYELEMENT_WEAK",
        joinColumns = @JoinColumn(name = "ENEMY_ID"),
        inverseJoinColumns = @JoinColumn(name = "ELEMENT_ID")
    )
    private List<EEnemy> weakEnemies;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        }) 
    @Column(name = "RESISTENEMIES")
    @JoinTable(
        name = "ENEMYELEMENT_RESIST",
        joinColumns = @JoinColumn(name = "ENEMY_ID"),
        inverseJoinColumns = @JoinColumn(name = "ELEMENT_ID")
    )
    private List<EEnemy> resistEnemies;   

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        }) 
    @Column(name = "IMMUNEENEMIES")
    @JoinTable(
        name = "ENEMYELEMENT_IMMUNE",
        joinColumns = @JoinColumn(name = "ENEMY_ID"),
        inverseJoinColumns = @JoinColumn(name = "ELEMENT_ID")
    )
    private List<EEnemy> immuneEnemies;

    public EEnemyElement() {}

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

    @Override
    public String toString() {
        return "Element id: " + getId() + " name: " + getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EEnemyElement)) return false;
        EEnemyElement element = (EEnemyElement) obj;
        return Objects.equals(getId(), element.id);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
