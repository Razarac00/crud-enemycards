package com.razarac.enemycrud.repository;

import java.util.List;

import com.razarac.enemycrud.entities.EEnemyElement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElementCrudRepository extends JpaRepository<EEnemyElement, Long>{
    List<EEnemyElement> findByName(String elementName);
}
