package org.sascha.qrbackend.User.DTO;

public class UpdateExistingOfferResponse {

    private boolean success;
    private String message;

    public UpdateExistingOfferResponse() {
    }

    public UpdateExistingOfferResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}

