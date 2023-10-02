package com.nttdata.bank.model.service;

import java.util.List;

import com.nttdata.bank.model.entity.Movement;

public interface MovementService {
	
	public Movement saveMovement(Movement movement);
	public Movement getMovementById(Long id);
	public List<Movement> getAllMovements();
}
