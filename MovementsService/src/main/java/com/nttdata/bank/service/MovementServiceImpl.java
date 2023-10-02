package com.nttdata.bank.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.bank.model.entity.Movement;
import com.nttdata.bank.model.repository.MovementRepository;
import com.nttdata.bank.model.service.MovementService;
import com.nttdata.bank.model.util.ResourceNotFoundException;

@Service
public class MovementServiceImpl implements MovementService{
	
	@Autowired
	private MovementRepository movementRepository;
	
	@Override
	public Movement saveMovement(Movement movement) {
		movement.setDate(LocalDateTime.now());
		return movementRepository.save(movement);
	}

	@Override
	public Movement getMovementById(Long id) {
		return movementRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Movement not found"));
	}

	@Override
	public List<Movement> getAllMovements() {
		return movementRepository.findAll();
	}

}
