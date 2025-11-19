package org.sascha.qrbackend.EnterOfferUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EnterOfferUserRepo extends JpaRepository<EnterOfferUser, UUID> {

    boolean existsByUserIdAndCompanyId(UUID userId, UUID companyId);

    Optional<EnterOfferUser> findByUserIdAndCompanyId(UUID userId, UUID companyId);





}
