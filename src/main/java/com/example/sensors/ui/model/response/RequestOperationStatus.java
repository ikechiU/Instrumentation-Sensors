package com.example.sensors.ui.model.response;

public enum RequestOperationStatus {
    ERROR_ONLY_AN_ADMIN_CAN_DELETE_THIS_SENSOR("Error, only an admin can delete this sensor"), SUCCESS("Success");

    private String message;

    RequestOperationStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
