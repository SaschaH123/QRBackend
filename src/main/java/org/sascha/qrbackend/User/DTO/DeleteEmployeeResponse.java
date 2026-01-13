package org.sascha.qrbackend.User.DTO;

public class DeleteEmployeeResponse {

    private boolean success;
    private String message;

    public DeleteEmployeeResponse() {
    }

    public DeleteEmployeeResponse(boolean success, String message) {
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
