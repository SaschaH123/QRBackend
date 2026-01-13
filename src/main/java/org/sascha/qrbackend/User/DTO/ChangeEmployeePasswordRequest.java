package org.sascha.qrbackend.User.DTO;

public class ChangeEmployeePasswordRequest {

    private String confirmPassword;

    public ChangeEmployeePasswordRequest() {
    }

    public ChangeEmployeePasswordRequest(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
