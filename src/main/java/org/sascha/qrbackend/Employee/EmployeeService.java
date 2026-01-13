package org.sascha.qrbackend.Employee;

import org.sascha.qrbackend.User.DTO.EmployeeResponseDTO;
import org.sascha.qrbackend.User.DTO.GetEmployeeListResponse;

import java.util.List;

public interface EmployeeService {

    public EmployeeResponseDTO registerEmployee(String companyId, String employeeFirstName, String employeeLastName, String employeeEmail, String employeeRole, String password);
    public List<GetEmployeeListResponse> getAllEmployeesByCompanyId(String companyId);
}
