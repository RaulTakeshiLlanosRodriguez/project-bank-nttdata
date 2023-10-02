package com.nttdata.bank.model.service;

import java.util.List;

import com.nttdata.bank.model.entity.Customer;

public interface CustomerService{
	
	public Customer saveCustomer(Customer customer);
	public List<Customer> getAllCustomers();
	public Customer getCustomerById(Long id);
}
