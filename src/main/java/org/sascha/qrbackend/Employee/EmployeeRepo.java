package org.sascha.qrbackend.Employee;

import org.sascha.qrbackend.EnterOfferUser.EnterOfferUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepo extends JpaRepository<Employee, UUID> {

    List<Employee> findByCompany_CompanyId(UUID companyId);

    Optional<Employee> findByEmployeeIdAndCompany_CompanyId(UUID employeeId, UUID companyId);
    boolean existsByEmployeeIdAndCompany_CompanyId(UUID employeeId, UUID companyId);

    Optional<Employee> findByEmployeeEmail(String employeeEmail);

    Boolean existsByEmployeeEmail(String employeeEmail);

}
