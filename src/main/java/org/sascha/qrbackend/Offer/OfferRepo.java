package org.sascha.qrbackend.Offer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfferRepo extends JpaRepository<Offer, String> {

    List<Offer> findByCompany_CompanyId(UUID companyId);

    Optional<Offer> findByOfferId(String offerId);

    boolean existsByCompany_CompanyId(UUID companyId);




}
