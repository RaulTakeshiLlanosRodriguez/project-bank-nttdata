package com.nttdata.bank.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bank.model.entity.Movement;

@Repository
public interface MovementRepository extends JpaRepository<Movement, Long>{
	
	
}
