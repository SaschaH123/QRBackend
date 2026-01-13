package org.sascha.qrbackend.Company;

import org.sascha.qrbackend.User.DTO.CompanyRegisterResponse;
import org.sascha.qrbackend.User.DTO.GetCompanyListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepo companyRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CompanyRegisterResponse registerCompany(String companyName, String firstName, String lastName, String street, String city, Integer plz, String companyDesc, String companyEmail, String companyPassword) {

        if (companyRepo.existsByCompanyEmail(companyEmail)) {
            throw new RuntimeException("Email schon vergeben");
        }

        String hashedPassword = passwordEncoder.encode(companyPassword);
        Company company = new Company(companyName, firstName, lastName, street, city, plz, companyDesc, companyEmail, hashedPassword);

        companyRepo.save(company);

        return new CompanyRegisterResponse(
                true,
                "Company erfolgreich erstellt",
                company.getCompanyId().toString()
        );
    }

    public List<GetCompanyListResponse> getAllCompanies() {
        return companyRepo.findAll().stream()
                .map(c -> new GetCompanyListResponse(
                        c.getCompanyId().toString(),
                        c.getCompanyName(),
                        c.getCompanyDesc(),
                        c.getImageUrl()
                ))
                .toList();

    }
}
