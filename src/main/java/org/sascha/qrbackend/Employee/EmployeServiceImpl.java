package org.sascha.qrbackend.Employee;

import org.sascha.qrbackend.Company.CompanyRepo;
import org.sascha.qrbackend.User.DTO.EmployeeResponseDTO;
import org.sascha.qrbackend.User.DTO.GetEmployeeListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeServiceImpl implements EmployeeService {


    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    CompanyRepo companyRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public EmployeeResponseDTO registerEmployee(String companyId, String employeeFirstName, String employeeLastName, String employeeEmail, String employeeRole, String password) {

        if(employeeRepo.existsByEmployeeEmail(employeeEmail)) {
            return new EmployeeResponseDTO(
                    false,
                    "Email ist bereits vergeben!",
                    "Keine Rolle"


            );
        }


        var company = companyRepo.findById(UUID.fromString(companyId))
                .orElseThrow(() -> new RuntimeException("Company not found" + companyId));

        String hashedPassword = passwordEncoder.encode(password);

        Employee employee = new Employee(
                company,
                employeeFirstName,
                employeeLastName,
                employeeEmail,
                employeeRole,
                hashedPassword

        );
        employeeRepo.save(employee);

        return new EmployeeResponseDTO(
                true,
                "Mitarbeiter erfolgreich registriert",
                employeeRole
        );
    }

    public List<GetEmployeeListResponse> getAllEmployeesByCompanyId(String companyId) {

        var companyUUID = UUID.fromString(companyId);

        List<Employee> employees = employeeRepo.findByCompany_CompanyId(companyUUID);

        if (employees.isEmpty()) {
            return Collections.emptyList();
        }



        return employees.stream()
                .map(employee -> new GetEmployeeListResponse(

                        true,
                        employee.getEmployeeId().toString(),
                        "Mitarbeiter gefunden",
                        employee.getEmployeeFirstName(),
                        employee.getEmployeeLastName(),
                        employee.getRole()

                )).collect(Collectors.toList());



    }



}

