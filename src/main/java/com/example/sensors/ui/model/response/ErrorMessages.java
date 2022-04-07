package com.example.sensors.ui.model.response;

public enum ErrorMessages {

    TITLE_ALREADY_EXISTS("Title already exists"),
    DESCRIPTION_ALREADY_EXISTS("Description already exists"),
    MISSING_REQUIRED_FIELD("Missing required field"),
    NO_RECORD_FOUND("Your query does not match any sensor"),
    NO_IMAGE_FOUND("Image path is empty"),
    ERROR_UPDATING_SENSOR("Only an admin can update this sensor");

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
