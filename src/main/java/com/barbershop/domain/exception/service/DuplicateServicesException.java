package com.barbershop.domain.exception.service;

public class DuplicateServicesException extends RuntimeException {
    public DuplicateServicesException(String name) {
        super("Service with name '" + name + "' already exists.");
    }
}