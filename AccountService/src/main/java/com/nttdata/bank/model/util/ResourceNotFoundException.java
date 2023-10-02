package com.nttdata.bank.model.util;

public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String mensaje) {
		super(mensaje);
	}
}
