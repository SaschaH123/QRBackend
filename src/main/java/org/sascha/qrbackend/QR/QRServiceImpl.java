package org.sascha.qrbackend.QR;

import org.sascha.qrbackend.Offer.Offer;
import org.sascha.qrbackend.Offer.OfferRepo;
import org.sascha.qrbackend.User.DTO.QRIssuerDTORequest;
import org.sascha.qrbackend.User.DTO.QRIssuerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
public class QRServiceImpl implements QRService{

    @Autowired
    QRTokenRepo qrTokenRepo;

    @Autowired
    OfferRepo offerRepo;

    public QRIssuerResponse issue(String userId, String offerId) {

        var offer = offerRepo.findByOfferId((offerId))
                .orElseThrow(() -> new IllegalArgumentException("Offer not found"));

        int points = offer.getOfferPoints();
        String companyId = String.valueOf(offer.getCompany().getCompanyId());

        Instant expiresAt = Instant.now().plus(Duration.ofMinutes(2));

        String rawToken = UUID.randomUUID() + "." + offerId + "." + System.currentTimeMillis();
        String tokenHash = sha256(rawToken);

        QRTokenEntitiy qrToken = new QRTokenEntitiy(
                tokenHash,
                userId,
                companyId,
                offerId,
                points,
                expiresAt,
                null);

        qrTokenRepo.save(qrToken);

        return new QRIssuerResponse(
                rawToken,
                true,
                offer.getOfferName(),
                expiresAt
        );

    }

    private String sha256(String raw) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return Base64.getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(digest.digest(raw.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
