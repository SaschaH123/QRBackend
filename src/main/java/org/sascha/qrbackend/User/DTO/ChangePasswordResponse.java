package org.sascha.qrbackend.User.DTO;

public class ChangePasswordResponse {

    public boolean success;
    public String message;

    public ChangePasswordResponse() {
    }

    public ChangePasswordResponse(boolean success, String message) {
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
