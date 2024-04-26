package com.msvc.hotel.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException() {
        super("Resource Not Found in the System");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
