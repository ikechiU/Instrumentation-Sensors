package com.example.sensors.ui.model.response;

public enum ErrorMessages {

    RECORD_ALREADY_EXISTS("Record already exists"),
    MISSING_REQUIRED_FIELD("Missing required field"),
    NO_RECORD_FOUND("Your query does not match any sensor");


    private String errorMessage;

    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
