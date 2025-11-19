package org.sascha.qrbackend.EnterOfferUser;

import org.sascha.qrbackend.User.DTO.EnterOfferUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EnterOfferUserServiceImpl implements  EnterOfferUserService {


    @Autowired
    EnterOfferUserRepo enterOfferUserRepo;


    public EnterOfferUserResponse searchFirstEntryUserOffer(String userId, String companyId) {

        var userUUID = UUID.fromString(userId);
        var companyUUID = UUID.fromString(companyId);

        Optional<EnterOfferUser> existing = enterOfferUserRepo.findByUserIdAndCompanyId(userUUID, companyUUID);

        if(existing.isPresent()) {
            EnterOfferUser entry = existing.get();

            return new EnterOfferUserResponse(
                    true,
                    "Eintrag existiert bereits",
                    entry.getUserPoints()
            );
        }


        EnterOfferUser newEntry = new EnterOfferUser(
                userUUID,
                companyUUID,
                0.0
        );


        enterOfferUserRepo.save(newEntry);

        return new EnterOfferUserResponse(
                true,
                "Neuer Eintrag erstellt",
                0.0
        );

    }
}
