package org.sascha.qrbackend.EnterOfferUser;

import org.sascha.qrbackend.User.DTO.EnterOfferUserResponse;

public interface EnterOfferUserService {

    public EnterOfferUserResponse searchFirstEntryUserOffer(String userId, String companyId);
}
