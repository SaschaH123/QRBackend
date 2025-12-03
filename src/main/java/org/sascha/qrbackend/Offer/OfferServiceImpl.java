package org.sascha.qrbackend.Offer;

import org.sascha.qrbackend.Company.CompanyRepo;
import org.sascha.qrbackend.EnterOfferUser.EnterOfferUser;
import org.sascha.qrbackend.EnterOfferUser.EnterOfferUserRepo;
import org.sascha.qrbackend.User.DTO.CreateOfferResponse;
import org.sascha.qrbackend.User.DTO.GetSingleCompanyListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    OfferRepo offerRepo;

    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    EnterOfferUserRepo enterOfferUserRepo;

    public Offer createOffer(String companyId, String offerName, String offerDesc, Integer offerPoints, String offerStatus) {

        var company = companyRepo.findById(UUID.fromString(companyId))
                .orElseThrow(() -> new RuntimeException("User not found" + companyId));

        OfferStatus status = OfferStatus.valueOf(offerStatus);

        Offer offer = new Offer(
                company,
                offerName,
                offerDesc,
                offerPoints,
                status,
                null
        );

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
                        offer.getCreatedAt().toString(),
                        offer.getImageURL()
                ))
                .collect(Collectors.toList());

    }

    public List<GetSingleCompanyListResponse> getSingleOffersByCompanyId(String companyId, String userId) {

        var companyUUID = UUID.fromString(companyId);
        var userUUID = UUID.fromString(userId);
        List<Offer> singleOffers = offerRepo.findByCompany_CompanyId(companyUUID);

        EnterOfferUser enter = enterOfferUserRepo.findByUserIdAndCompanyId(userUUID, companyUUID)
                .orElseThrow(() -> new RuntimeException("Kein Offer gefunden"));

        Set<String> redeemedOffers = (enter != null)
                ? enter.getRedeemedOffers()
                : new HashSet<>();


        return singleOffers.stream()
                .map(singleOffer -> {
                    String offerId = singleOffer.getOfferId().toString();

                    // ✅ Check ob DIESER User das Offer eingelöst hat
                    boolean isRedeemed = redeemedOffers.contains(offerId);
                    System.out.println("Eingelöst?:" + isRedeemed);

                    return new GetSingleCompanyListResponse(
                            offerId,
                            true,
                            singleOffer.getCompany().getCompanyId().toString(),
                            singleOffer.getOfferDesc(),
                            singleOffer.getOfferName(),
                            singleOffer.getOfferPoints(),
                            isRedeemed,// ✅ Nur für diesen User!
                            singleOffer.getImageURL()
                    );
                })
                .collect(Collectors.toList());

    }

}
