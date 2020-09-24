package com.udacity.jdnd.course3.critter.service.exception;

public class ScheduleNotCreatedException extends RuntimeException {

    public ScheduleNotCreatedException(String message) {
        super("Error creating schedule. " + message);
    }
}
