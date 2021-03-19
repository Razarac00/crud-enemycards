package com.razarac.enemycrud.repository;

import java.util.List;

import com.razarac.enemycrud.entities.EEnemy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnemyCrudRepository extends JpaRepository<EEnemy, Long> {
    List<EEnemy> findByNameContaining(String enemyName);
    Page<EEnemy> findByNameContaining(String enemyName, Pageable pageable);
}
