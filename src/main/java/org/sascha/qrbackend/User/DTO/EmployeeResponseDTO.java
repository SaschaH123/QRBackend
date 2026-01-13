package org.sascha.qrbackend.User.DTO;

public class EmployeeResponseDTO {

    private boolean success;
    private String message;
    private String employeeRole;

    public EmployeeResponseDTO() {
    }

    public EmployeeResponseDTO(boolean success, String message, String employeeRole) {
        this.success = success;
        this.message = message;
        this.employeeRole = employeeRole;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }
}
