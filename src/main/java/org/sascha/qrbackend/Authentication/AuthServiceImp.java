package org.sascha.qrbackend.Authentication;

import org.sascha.qrbackend.Company.Company;
import org.sascha.qrbackend.Company.CompanyRepo;
import org.sascha.qrbackend.User.DTO.LoginResponse;
import org.sascha.qrbackend.User.User;
import org.sascha.qrbackend.User.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public LoginResponse loginUser(String email, String password) {

        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            User u = user.get();
            if (passwordEncoder.matches(password, u.getPassword())) {
                System.out.println("Password verified");
                String token = jwtTokenProvider.generateToken(u.getId().toString(), "USER");
                System.out.println("Token: " + token);
                return new LoginResponse(
                        true,
                        "Login erfolgreich",
                        u.getId().toString(),
                        "USER",
                        u.getFirstName() + " " + u.getLastName(),
                        token,
                        u.getUserPoints()
                );
            }
        }
        Optional<Company> company = companyRepo.findByCompanyEmail(email);
        if (company.isPresent()) {
            Company c = company.get();
            if (passwordEncoder.matches(password, c.getCompanyPassword())) {
                String token = jwtTokenProvider.generateToken(c.getCompanyId().toString(), "COMPANY");
                return new LoginResponse(
                        true,
                        "Login erfolgreich",
                        c.getCompanyId().toString(),
                        "COMPANY",
                        c.getFirstName() + " " + c.getLastName(),
                        token,
                        null
                );
            }

        }
        return new LoginResponse(false, "Email nicht registriert", null, null, null, null, null);
    }

}

