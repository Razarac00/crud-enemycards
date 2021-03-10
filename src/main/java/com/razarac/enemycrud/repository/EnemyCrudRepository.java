package com.razarac.enemycrud.repository;

import com.razarac.enemycrud.entities.EEnemy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnemyCrudRepository extends JpaRepository<EEnemy, Long> {
}
