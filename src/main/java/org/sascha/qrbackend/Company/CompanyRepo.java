package org.sascha.qrbackend.Company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepo extends JpaRepository<Company, UUID> {

    Optional<Company> findByCompanyEmail(String companyEmail);
    boolean existsByCompanyEmail(String companyEmail);

    Optional<Company> findByCompanyName(String companyName);
    boolean existsByCompanyName(String companyName);

    Optional<Company> findByCompanyId(UUID companyId);
    boolean existsByCompanyId(UUID companyId);
}
