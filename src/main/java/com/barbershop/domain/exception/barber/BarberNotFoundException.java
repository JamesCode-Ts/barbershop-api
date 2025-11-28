package com.barbershop.domain.exception.barber;

public class BarberNotFoundException extends RuntimeException {
    public BarberNotFoundException(String id) {
        super("Barber not found: " + id);
    }
}