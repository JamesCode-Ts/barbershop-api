package com.barbershop.domain.exception.client;


public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String id) {
        super("Client not found: " + id);
    }
}