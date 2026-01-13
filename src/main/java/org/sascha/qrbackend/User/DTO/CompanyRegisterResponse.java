package org.sascha.qrbackend.User.DTO;

public class CompanyRegisterResponse {
    public boolean success;
    public String message;
    public String companyId;

    public CompanyRegisterResponse() {}

    public CompanyRegisterResponse(boolean success, String message, String companyId) {
        this.success = success;
        this.message = message;
        this.companyId = companyId;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getCompanyId() {
        return companyId;
    }
}

