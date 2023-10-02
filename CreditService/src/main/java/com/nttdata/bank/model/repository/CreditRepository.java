package com.nttdata.bank.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.bank.model.entity.Credit;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long>{
	
	public List<Credit> findByCustomerIdAndCreditType(Integer customerId, String creditType);
	public List<Credit> findByCustomerId(Integer customerId);
}
