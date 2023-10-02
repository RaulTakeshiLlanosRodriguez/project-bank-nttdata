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

import com.nttdata.bank.model.entity.Customer;
import com.nttdata.bank.model.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody Customer customer) {
		
		if(!(customer.getTypeCustomer().equalsIgnoreCase("personal") || customer.getTypeCustomer().equalsIgnoreCase("empresarial"))) {
			return ResponseEntity.badRequest().body("Type customer not valid");
		}else {
			customerService.saveCustomer(customer);
			return ResponseEntity.status(HttpStatus.CREATED).body("Customer created successfully");
		}
    }
	
	@GetMapping
	public ResponseEntity<List<Customer>> findAllCustomers(){
		return ResponseEntity.ok(customerService.getAllCustomers());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
		return ResponseEntity.ok(customerService.getCustomerById(id));
	}
	
}
