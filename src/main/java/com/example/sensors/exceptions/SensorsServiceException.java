package com.example.sensors.exceptions;

public class SensorsServiceException extends RuntimeException{

    private static final long serialVersionUID = 1348771109171435607L;

    public SensorsServiceException(String message)
    {
        super(message);
    }
}