package com.nttdata.bank.model.entity;

import lombok.Data;

@Data
public class Customer {
	private Long id;
	private String name;
	private String lastName;
	private String typeCustomer;
}
