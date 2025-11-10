package org.sascha.qrbackend.Offer;

import org.sascha.qrbackend.User.DTO.CreateOfferResponse;

import java.util.List;

public interface OfferService {

    Offer createOffer(String companyId, String offerName, String offerDesc, Integer offerPoints, String offerStatus);
    public List<CreateOfferResponse> getOffersbyUserId(String companyId);
}
