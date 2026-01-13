package org.sascha.qrbackend.Employee;

import org.sascha.qrbackend.User.DTO.*;
import org.sascha.qrbackend.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    @Autowired
    EmployeeRepo employeeRepo;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeResponseDTO> register(@RequestBody EmployeeRequestDTO request, Authentication authentication) {

        String companyId = authentication.getName();

        EmployeeResponseDTO response = employeeService.registerEmployee(companyId, request.employeeFirstName, request.employeeLastName, request.employeeEmail, request.getEmployeeRole(), request.getPassword());

        return ResponseEntity.ok(response);

    }

    @GetMapping("/all")
    public ResponseEntity<List<GetEmployeeListResponse>> getAllEmployeeByCompany(Authentication authentication) {

        String companyId = authentication.getName();

        List<GetEmployeeListResponse> response = employeeService.getAllEmployeesByCompanyId(companyId);

        System.out.println("GetEmployeeListResponse = " + response);


        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/delete-employee/{employeeId}")
    public ResponseEntity<DeleteEmployeeResponse> deleteEmployee(@PathVariable String employeeId, Authentication authentication) {

        String companyId = authentication.getName();

        var employeeUUID = UUID.fromString(employeeId);
        var companyUUID = UUID.fromString(companyId);

        Employee employee = employeeRepo.findByEmployeeIdAndCompany_CompanyId(employeeUUID, companyUUID)
                .orElseThrow(() -> new RuntimeException("Kein Mitarbeiter gefunden"));

        String firstName = employee.getEmployeeFirstName();
        String lastName = employee.getEmployeeLastName();


        System.out.println("Lösche Mitarbeiter");
        employeeRepo.delete(employee);

        return ResponseEntity.ok(
                new DeleteEmployeeResponse(
                        true,
                        "Mitarbeiter " + firstName + " " + lastName + " wurde gelöscht!"
                )
        );


    }

    @PutMapping("/change-password/{employeeId}")
    public ResponseEntity<ChangeEmployeePasswordResponse> changePassword(
            @PathVariable String employeeId,
            @RequestBody ChangeEmployeePasswordRequest request) {

        try {
            UUID employeeUUID = UUID.fromString(employeeId);

            System.out.println("🔐 Change password for employee: " + employeeId);
            System.out.println("📝 New password length: " + request.getConfirmPassword().length());

            Optional<Employee> employeeOpt = employeeRepo.findById(employeeUUID);

            if (employeeOpt.isPresent()) {
                Employee employee = employeeOpt.get();

                if (passwordEncoder.matches(request.getConfirmPassword(), employee.getPassword())) {
                    return ResponseEntity.badRequest()
                            .body(new ChangeEmployeePasswordResponse(
                            false,
                            "❌ Neues Passwort darf nicht dasselbe wie das alte sein!"
                    ));
                }

                String hashedPassword = passwordEncoder.encode(request.getConfirmPassword());
                System.out.println("✅ Hashed password: " + hashedPassword.substring(0, 20) + "...");

                employee.setPassword(hashedPassword);
                employee.setUpdatedAt(LocalDateTime.now());
                employeeRepo.save(employee);

                System.out.println("✅ Password changed successfully for: " + employee.getEmployeeId());

                return ResponseEntity.ok()
                        .body(new ChangeEmployeePasswordResponse(
                                true,
                                "✅ Passwort erfolgreich geändert"
                        ));
            } else {
                System.out.println("❌ Employee not found: " + employeeId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ChangeEmployeePasswordResponse(
                                false,
                                "❌ Mitarbeiter nicht gefunden"
                        ));
            }

        } catch (IllegalArgumentException e) {
            System.err.println("❌ Invalid UUID: " + employeeId);
            return ResponseEntity.badRequest()
                    .body(new ChangeEmployeePasswordResponse(
                            false,
                            "❌ Ungültige Mitarbeiter-ID"
                    ));
        } catch (Exception e) {
            System.err.println("❌ Error changing password: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ChangeEmployeePasswordResponse(
                            false,
                            "❌ Ein Fehler ist aufgetreten: " + e.getMessage()
                    ));
        }
    }


}
