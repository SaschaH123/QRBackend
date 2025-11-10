package org.sascha.qrbackend.Company;

import org.sascha.qrbackend.User.DTO.GetCompanyListResponse;

import java.util.List;

public interface CompanyService {
    Company registerCompany(String companyName, String firstName, String lastName, String street, String city, Integer plz, String companyEmail, String companyPassword);

    List<GetCompanyListResponse> getAllCompanies();
}
