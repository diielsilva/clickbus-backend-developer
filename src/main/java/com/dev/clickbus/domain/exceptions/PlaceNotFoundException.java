package com.dev.clickbus.domain.exceptions;

public class PlaceNotFoundException extends RuntimeException {

    public PlaceNotFoundException(String message) {
        super(message);
    }

}
