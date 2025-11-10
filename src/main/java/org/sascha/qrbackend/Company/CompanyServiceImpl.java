package org.sascha.qrbackend.Company;

import org.sascha.qrbackend.User.DTO.GetCompanyListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepo companyRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Company registerCompany(String companyName, String firstName, String lastName, String street, String city, Integer plz, String companyEmail, String companyPassword) {

        if (companyRepo.existsByCompanyEmail(companyEmail)) {
            throw new RuntimeException("Email schon vergeben");
        }

        String hashedPassword = passwordEncoder.encode(companyPassword);
        Company company = new Company(companyName, firstName, lastName, street, city, plz, companyEmail, hashedPassword);
        return companyRepo.save(company);
    }

    public List<GetCompanyListResponse> getAllCompanies() {
        return companyRepo.findAll().stream()
                .map(c -> new GetCompanyListResponse(
                        c.getCompanyId().toString(),
                        c.getCompanyName()
                ))
                .toList();

    }
}
