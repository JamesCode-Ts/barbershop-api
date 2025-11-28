package com.barbershop.domain.exception.service;

public class ServicesNotFoundException extends RuntimeException {
    public ServicesNotFoundException(String id) {
        super("Service not found: " + id);
    }
}
