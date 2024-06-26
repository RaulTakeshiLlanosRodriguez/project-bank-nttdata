package com.nttdata.bank.model.service;

import java.util.List;

import com.nttdata.bank.model.entity.Credit;

public interface CreditService {
	
	public List<Credit> getCreditByCustomer(Integer customerId);
	public Credit createCreditPersonal(Credit credit);
	public Credit createCreditBusiness(Credit credit);
	public Credit getCreditById(Long id);
	public List<Credit> getAllCredits();
	public String makeCreditPayment(Long id, Integer paymentAumont);
}
