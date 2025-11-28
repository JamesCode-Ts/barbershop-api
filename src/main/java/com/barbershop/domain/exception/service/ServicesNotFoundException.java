package com.barbershop.domain.exception.service;

public class ServiceNotFoundException extends RuntimeException {
    public ServiceNotFoundException(String id) {
        super("Service not found: " + id);
    }
}
