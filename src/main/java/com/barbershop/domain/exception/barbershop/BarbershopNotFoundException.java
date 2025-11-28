package com.barbershop.domain.exception.barbershop;

import com.barbershop.infrastructure.exception.RequestException;

public class BarbershopNotFoundException extends RequestException {
    public BarbershopNotFoundException(String id) {
        super("BarbershopNotFound", "Barbershop with ID " + id + " not found");
    }
}
