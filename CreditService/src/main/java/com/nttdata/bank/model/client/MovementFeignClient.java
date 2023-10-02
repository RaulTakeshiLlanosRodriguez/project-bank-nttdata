package com.nttdata.bank.model.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nttdata.bank.model.entity.Movement;

@FeignClient(name = "movement-service", url = "localhost:8091")
public interface MovementFeignClient {
	
	@PostMapping("/movements")
	public ResponseEntity<String> createMovement(@RequestBody Movement movement);
	
	@GetMapping("/movements/{id}")
	public ResponseEntity<Movement> getMovementById(@PathVariable Long id);
}
