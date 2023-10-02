package com.nttdata.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nttdata.bank.model.client.CustomerFeignClient;
import com.nttdata.bank.model.client.MovementFeignClient;
import com.nttdata.bank.model.entity.Account;
import com.nttdata.bank.model.entity.Customer;
import com.nttdata.bank.model.entity.Movement;
import com.nttdata.bank.model.repository.AccountRepository;
import com.nttdata.bank.model.service.AccountService;
import com.nttdata.bank.model.util.ResourceNotFoundException;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CustomerFeignClient customerFeign;
	
	@Autowired
	private MovementFeignClient movementFeign;

	@Override
	public Account createAccount(Account account) {
		Customer customer = customerFeign.getCustomerById(account.getCustomerId()).getBody();
		account.setCustomer(customer);
		return accountRepository.save(account);
	}

	@Override
	public boolean existsAccountOfSameType(Long customerId, String typeAccount) {
		
		List<Account> accountOfSameCustomer = accountRepository.findByCustomerId(customerId);
		return accountOfSameCustomer.stream().
				anyMatch(account -> account.getTypeAccount().equalsIgnoreCase(typeAccount));
	}

	@Override
	public Account getAccountById(Long id) {
		
		Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
		Customer customer = customerFeign.getCustomerById(account.getCustomerId()).getBody();
		account.setCustomer(customer);
		return account;
	}

	@Override
	public boolean isCustomerEmpresarial(Long customerId) {
		
		Customer customer = customerFeign.getCustomerById(customerId).getBody();
		return customer != null && customer.getTypeCustomer().equalsIgnoreCase("empresarial");
	}

	@Override
	public List<Account> getAccountByCustomer(Long id) {
		
		Customer customer = customerFeign.getCustomerById(id).getBody();
		List<Account> listAccounts = accountRepository.findByCustomerId(id);
		listAccounts.forEach(account -> account.setCustomer(customer));
		return listAccounts;
	}

	@Override
	public Account makeDeposit(Long accountId, Double amount) {
		Account account = accountRepository.findById(accountId).orElse(null);
		if(account != null) {
			account.setBalance(account.getBalance() + amount);
			accountRepository.save(account);
			
			Movement movement = new Movement();
			movement.setAccountId(accountId);
			movement.setTypeMovement("deposit");
			movement.setTypeProduct("account");
			movement.setAmount(amount);
			
			ResponseEntity<String> response = movementFeign.createMovement(movement);
			
			if(response.getStatusCode() == HttpStatus.CREATED) {
				return account;
			}
		}
		
		return null;
	}

	@Override
	public String withdraw(Long accountId, Double amount) {
		Account account = accountRepository.findById(accountId).orElse(null);
		if(account != null) {
			if(account.getBalance() >= amount) {
				account.setBalance(account.getBalance() - amount);
				accountRepository.save(account);
				
				Movement movement = new Movement();
				movement.setAccountId(accountId);
				movement.setTypeMovement("withdraw");
				movement.setTypeProduct("account");
				movement.setAmount(amount);
				
				ResponseEntity<String> response = movementFeign.createMovement(movement);
				
				if(response.getStatusCode() == HttpStatus.CREATED) {
					return "Withdraw: "+amount;
				}
			}else {
				return "balance is less than amount";
			}
		}
		
		return null;
	}

	@Override
	public Double consultBalanceAccount(Long id) {
		Account account = accountRepository.findById(id).orElse(null);
		if(account != null) {
			return account.getBalance();
		}else {
			throw new ResourceNotFoundException("Account not found");
		}
	}	

}
