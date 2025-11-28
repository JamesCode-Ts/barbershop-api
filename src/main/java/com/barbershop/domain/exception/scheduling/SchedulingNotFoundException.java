package com.barbershop.domain.exception.scheduling;

public class SchedulingNotFoundException extends RuntimeException {
    public SchedulingNotFoundException(String id) {
        super("Scheduling not found: " + id);
    }
}