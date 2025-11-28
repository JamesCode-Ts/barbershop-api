package com.barbershop.domain.exception.barbershop;

import com.barbershop.infrastructure.exception.RequestException;

public class DuplicateBarbershopException extends RequestException {
    public DuplicateBarbershopException(String name) {
        super("DuplicateBarbershop", "Barbershop with name '" + name + "' already exists.");
    }
}
