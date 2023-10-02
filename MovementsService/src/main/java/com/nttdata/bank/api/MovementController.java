package com.nttdata.bank.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bank.model.entity.Movement;
import com.nttdata.bank.model.service.MovementService;

@RestController
@RequestMapping("/movements")
public class MovementController {
	
	@Autowired
	private MovementService movementService;
	
	@PostMapping
	public ResponseEntity<String> createMovement(@RequestBody Movement movement){
		movementService.saveMovement(movement);
		return ResponseEntity.status(HttpStatus.CREATED).body("Movement created successfully");
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Movement> getMovementById(@PathVariable Long id){
		return ResponseEntity.ok(movementService.getMovementById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Movement>> getAllMovements(){
		return ResponseEntity.ok(movementService.getAllMovements());
	}
}
