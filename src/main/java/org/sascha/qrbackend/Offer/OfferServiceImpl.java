package org.sascha.qrbackend.Offer;

import org.sascha.qrbackend.Company.CompanyRepo;
import org.sascha.qrbackend.User.DTO.CreateOfferResponse;
import org.sascha.qrbackend.User.DTO.GetSingleCompanyListResponse;
import org.sascha.qrbackend.User.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    OfferRepo offerRepo;

    @Autowired
    CompanyRepo companyRepo;

    public Offer createOffer(String companyId, String offerName, String offerDesc, Integer offerPoints, String offerStatus) {

        var company = companyRepo.findById(UUID.fromString(companyId))
                .orElseThrow(() -> new RuntimeException("User not found" + companyId));

        OfferStatus status = OfferStatus.valueOf(offerStatus);

        Offer offer = new Offer(
                company,
                offerName,
                offerDesc,
                offerPoints,
                status);

        return offerRepo.save(offer);
    }

    public List<CreateOfferResponse> getOffersbyUserId(String companyId) {

        var companyUUID = UUID.fromString(companyId);

        List <Offer> offers = offerRepo.findByCompany_CompanyId(companyUUID);

        if (offers == null) {
            return new ArrayList<>(); // statt null
        }

        return offers.stream()
                .map(offer -> new CreateOfferResponse(
                        true,
                        offer.getOfferId().toString(),
                        offer.getCompany().getCompanyId().toString(),
                        offer.getOfferName(),
                        offer.getOfferDesc(),
                        offer.getOfferPoints(),
                        offer.getOfferStatus().toString(),
                        offer.getCreatedAt().toString()
                ))
                .collect(Collectors.toList());

    }

    public List<GetSingleCompanyListResponse> getSingleOffersByCompanyId(String companyId) {

        var companyUUID = UUID.fromString(companyId);
        List<Offer> singleOffers = offerRepo.findByCompany_CompanyId(companyUUID);

        return singleOffers.stream()
                .map(singleOffer -> new GetSingleCompanyListResponse(
                        singleOffer.getOfferId().toString(),
                        true,
                        singleOffer.getCompany().getCompanyId().toString(),
                        singleOffer.getOfferDesc(),
                        singleOffer.getOfferName(),
                        singleOffer.getOfferPoints()
                ))
                .collect(Collectors.toList());

    }

}
