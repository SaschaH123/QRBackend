package org.sascha.qrbackend.User.DTO;

public class EmployeeRequestDTO {

    public String employeeFirstName;
    public String employeeLastName;
    public String employeeEmail;
    public String employeeRole;
    public String password;

    public EmployeeRequestDTO() {
    }

    public EmployeeRequestDTO( String employeeFirstName, String employeeEmail, String employeeLastName, String employeeRole, String password) {
        this.employeeFirstName = employeeFirstName;
        this.employeeEmail = employeeEmail;
        this.employeeLastName = employeeLastName;
        this.employeeRole = employeeRole;
        this.password = password;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
