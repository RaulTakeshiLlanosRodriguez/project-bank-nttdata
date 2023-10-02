package com.nttdata.bank.model.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Movement {
	private Long id;
	private Long accountId;
	private String typeMovement;
	private String typeProduct;
	private Double amount;
	private LocalDateTime date;
}
