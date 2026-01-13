package org.sascha.qrbackend.Authentication;

import org.sascha.qrbackend.Company.Company;
import org.sascha.qrbackend.Company.CompanyRepo;
import org.sascha.qrbackend.Employee.Employee;
import org.sascha.qrbackend.Employee.EmployeeRepo;
import org.sascha.qrbackend.User.DTO.LoginResponse;
import org.sascha.qrbackend.User.User;
import org.sascha.qrbackend.User.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private Map<String, Integer> failedAttempts = new ConcurrentHashMap<>();
    private static final int MAX_ATTEMPTS = 5;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public LoginResponse loginUser(String email, String password) {

        if(failedAttempts.getOrDefault(email, 0) >= MAX_ATTEMPTS) {
            return new LoginResponse(
                    false,
                    "Maximale Versuche aufgebraucht bitte versuchen Sie es später erneut",
                    null,
                    null,
                    null,
                    null,
                    null

            );
        }

        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            User u = user.get();
            if (passwordEncoder.matches(password, u.getPassword())) {
                System.out.println("Password verified");
                String token = jwtTokenProvider.generateToken(u.getId().toString(), "USER");
                System.out.println("Token: " + token);
                failedAttempts.remove(email);
                return new LoginResponse(
                        true,
                        "Login erfolgreich",
                        u.getId().toString(),
                        "USER",
                        u.getFirstName() + " " + u.getLastName(),
                        token,
                        u.getUserPoints()
                );
            } else {
                int attempts = failedAttempts.getOrDefault(email, 0)+1;
                failedAttempts.put(email, attempts);
                int showAttempts = MAX_ATTEMPTS - attempts;
                return new LoginResponse(
                        false,
                        "Falsches Passwort noch " + showAttempts + " versuche",
                        null,
                        null,
                        null,
                        null,
                        null

                        );
            }
        }
        Optional<Company> company = companyRepo.findByCompanyEmail(email);
        if (company.isPresent()) {
            Company c = company.get();
            if (passwordEncoder.matches(password, c.getCompanyPassword())) {
                String token = jwtTokenProvider.generateToken(c.getCompanyId().toString(), "COMPANY");
                failedAttempts.remove(email);
                return new LoginResponse(
                        true,
                        "Login erfolgreich",
                        c.getCompanyId().toString(),
                        "COMPANY",
                        c.getFirstName() + " " + c.getLastName(),
                        token,
                        null
                );
            } else {
                int attempts = failedAttempts.getOrDefault(email, 0)+1;
                failedAttempts.put(email, attempts);
                int showAttempts = MAX_ATTEMPTS - attempts;
                return new LoginResponse(
                        false,
                        "Falsches Passwort noch " + showAttempts + " versuche",
                        null,
                        null,
                        null,
                        null,
                        null

                );
            }

        }

        Optional<Employee> employee = employeeRepo.findByEmployeeEmail(email);
        if(employee.isPresent()) {
            Employee e = employee.get();
            if(passwordEncoder.matches(password, e.getPassword())) {
                if(e.getUpdatedAt() == null) {
                    return new LoginResponse(
                            false,
                            "1. Einloggen bitte Passwort ändern",
                            e.getEmployeeId().toString(),
                            null,
                            null,
                            null,
                            0
                    );
                } else {
                    String token = jwtTokenProvider.generateToken(e.getEmployeeId().toString(), e.getRole());
                    failedAttempts.remove(email);
                    return new LoginResponse(
                            true,
                            "Login erfolgreich",
                            e.getEmployeeId().toString(),
                            e.getRole(),
                            e.getEmployeeFirstName() + " " + e.getEmployeeLastName(),
                            token,
                            null
                    );
                }

            } else if(!passwordEncoder.matches(password, e.getPassword())) {
                int attempts = failedAttempts.getOrDefault(email, 0)+1;
                failedAttempts.put(email, attempts);
                int showAttempts = MAX_ATTEMPTS - attempts;
                System.out.println("Falsches Password!");
                return new LoginResponse(
                        false,
                        "Falsches Passwort noch " + showAttempts + " versuche",
                        null,
                        null,
                        null,
                        null,
                        null

                );
            }
        }

        return new LoginResponse(false, "Email nicht registriert", null, null, null, null, null);
    }

}

