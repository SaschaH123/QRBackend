package org.sascha.qrbackend.Company;

import org.sascha.qrbackend.User.DTO.CompanyRegisterResponse;
import org.sascha.qrbackend.User.DTO.GetCompanyListResponse;

import java.util.List;

public interface CompanyService {
    CompanyRegisterResponse registerCompany(String companyName, String firstName, String lastName, String street, String city, Integer plz, String companyDesc, String companyEmail, String companyPassword);

    List<GetCompanyListResponse> getAllCompanies();
}
