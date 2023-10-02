package com.nttdata.bank.model.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SavingsAccount extends Account{
	
	private Integer monthlyTransactionLimit;
}
