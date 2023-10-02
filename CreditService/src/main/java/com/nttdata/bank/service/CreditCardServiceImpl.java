package com.nttdata.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nttdata.bank.model.client.MovementFeignClient;
import com.nttdata.bank.model.entity.CreditCard;
import com.nttdata.bank.model.entity.Movement;
import com.nttdata.bank.model.repository.CreditCardRepository;
import com.nttdata.bank.model.service.CreditCardService;
import com.nttdata.bank.model.util.ResourceNotFoundException;

@Service
public class CreditCardServiceImpl implements CreditCardService{

	@Autowired
	private CreditCardRepository creditCardRepository;
	@Autowired
	private MovementFeignClient movementFeign;
	
	@Override
	public CreditCard createCreditCard(CreditCard creditCard) {
		return creditCardRepository.save(creditCard);
	}

	@Override
	public String loadConsumptionCard(Long id, Double consumptionAmount) {
		CreditCard creditCard  = creditCardRepository.findById(id).orElse(null);
		
		if(creditCard != null) {
			if(consumptionAmount <= 0) {
				return "Consumption amount is invalid, enter a number greater than 0";
			}
			
			if(consumptionAmount > creditCard.getCreditLimit()) {
				return "the consumption is greater than the limit credit";
			}
			
			creditCard.setCreditLimit(creditCard.getCreditLimit() - consumptionAmount);
			creditCardRepository.save(creditCard);
			
			Movement movement = new Movement();
			movement.setAccountId(id);
			movement.setTypeMovement("consumption");
			movement.setTypeProduct("creditCard");
			movement.setAmount(consumptionAmount);
			
			ResponseEntity<String> response = movementFeign.createMovement(movement);
			
			if(response.getStatusCode() == HttpStatus.CREATED) {
				return "Consumption: "+consumptionAmount;
			}
		}
		return "Credit Card not found";
	}

	@Override
	public Double getBalance(Long id) {
		CreditCard creditCard = creditCardRepository.findById(id).orElse(null);
		if(creditCard != null) {
			return creditCard.getCreditLimit();
		}else {
			throw new ResourceNotFoundException("CreditCard not found");
		}
	}

	@Override
	public CreditCard getCreditCardById(Long id) {
		return creditCardRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Credit card not found"));
	}

	@Override
	public List<CreditCard> getAllCreditCards() {
		return creditCardRepository.findAll();
	}

}
