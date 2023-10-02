package com.nttdata.bank.model.service;

import java.util.List;

import com.nttdata.bank.model.entity.CreditCard;

public interface CreditCardService {
	public CreditCard createCreditCard(CreditCard creditCard);
	public String loadConsumptionCard(Long id, Double consumptionAmount);
	public Double getBalance(Long id);
	public CreditCard getCreditCardById(Long id);
	public List<CreditCard> getAllCreditCards();
}
