package com.nttdata.bank.model.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nttdata.bank.model.entity.Customer;

@FeignClient(name = "customer-service", url = "localhost:8081")
public interface CustomerFeignClient {
	
	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id);
	
}
