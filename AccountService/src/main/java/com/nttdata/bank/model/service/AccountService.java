package com.nttdata.bank.model.service;

import java.util.List;

import com.nttdata.bank.model.entity.Account;

public interface AccountService {
	
	public Account createAccount(Account account);
	public boolean existsAccountOfSameType(Long customerId, String typeAccount);
	public boolean isCustomerEmpresarial(Long customerId);
	public Account getAccountById(Long id);
	public List<Account> getAccountByCustomer(Long id);
	public Account makeDeposit(Long accountId, Double amount);
	public String withdraw(Long accountId, Double amount);
	public Double consultBalanceAccount(Long id);
}
