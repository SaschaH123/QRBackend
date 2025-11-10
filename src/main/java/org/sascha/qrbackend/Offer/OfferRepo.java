package org.sascha.qrbackend.Offer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OfferRepo extends JpaRepository<Offer, UUID> {

    List<Offer> findByCompany_CompanyId(UUID companyId);



}
