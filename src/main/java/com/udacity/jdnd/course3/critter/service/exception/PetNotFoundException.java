package com.udacity.jdnd.course3.critter.service.exception;

public class PetNotFoundException extends RuntimeException {

    public PetNotFoundException(String message) {
        super(message);
    }
}
