package org.sascha.qrbackend.Company;

import org.sascha.qrbackend.User.DTO.CompanyRegisterResponse;
import org.sascha.qrbackend.User.DTO.CompanyRequestDTO;
import org.sascha.qrbackend.User.DTO.GetCompanyListResponse;
import org.sascha.qrbackend.User.DTO.RegisterRequest;
import org.sascha.qrbackend.User.User;
import org.sascha.qrbackend.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company/")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @PostMapping("/register")
    public ResponseEntity<CompanyRegisterResponse> register(@RequestBody CompanyRequestDTO request) {
        CompanyRegisterResponse response = companyService.registerCompany(request.getCompanyName(),request.getFirstName(), request.getLastName(), request.getStreet(), request.getCity(), request.getPlz(), request.getCompanyDesc(), request.getCompanyEmail(), request.getCompanyPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<GetCompanyListResponse>> getAllCompanies() {

        List<GetCompanyListResponse> companies = companyService.getAllCompanies();

        System.out.println("Get all Companies = " + companies);

        return ResponseEntity.ok(companies);

    }

}
