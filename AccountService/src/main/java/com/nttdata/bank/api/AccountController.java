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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bank.model.entity.Account;
import com.nttdata.bank.model.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping
	public ResponseEntity<String> createAccount(@RequestBody Account account){
		
		if(accountService.isCustomerEmpresarial(account.getCustomerId())) {
			if(!account.getTypeAccount().equalsIgnoreCase("checking")) {
				return ResponseEntity.badRequest().body("Business customer can only have checking accounts");
			}
		}else {
			if(accountService.existsAccountOfSameType(account.getCustomerId(), account.getTypeAccount())) {
				return ResponseEntity.badRequest().body("personal customer already has an account of the same type.");
			}
		}
		
		accountService.createAccount(account);
		return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
	}
	
	@GetMapping("/{id}")
	public Account getAccountById(@PathVariable Long id) {
		return accountService.getAccountById(id);
	}
	
	@GetMapping("/customer/{customerId}")
	public List<Account> getAccountsByCustomerId(@PathVariable Long customerId){
		return accountService.getAccountByCustomer(customerId);
	}
	
	@PostMapping("/{accountId}/deposit")
	public ResponseEntity<String> makeDeposit(@PathVariable Long accountId, @RequestParam Double amount){
		
		Account account = accountService.makeDeposit(accountId, amount);
		if (account != null) {
            return ResponseEntity.ok("Deposit: "+amount);
        }
        return ResponseEntity.notFound().build(); 
	}
	
	@PostMapping("/{accountId}/withdraw")
	public ResponseEntity<String> makeWithdraw(@PathVariable Long accountId, @RequestParam Double amount){
		String message = accountService.withdraw(accountId, amount);
		return ResponseEntity.ok(message); 
	}
	
	@GetMapping("/{accountId}/balance")
	public ResponseEntity<String> consultBalanceAccount(@PathVariable Long accountId){
		Double balance = accountService.consultBalanceAccount(accountId);
		return ResponseEntity.ok("Balance: "+balance);
	}
}
