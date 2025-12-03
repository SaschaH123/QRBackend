package org.sascha.qrbackend.User.DTO;

public class DeleteOfferResponse {
    private boolean success;
    private String message;

    public DeleteOfferResponse(boolean success, String message) {
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