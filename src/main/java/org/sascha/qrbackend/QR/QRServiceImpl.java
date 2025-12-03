package org.sascha.qrbackend.QR;

import org.sascha.qrbackend.EnterOfferUser.EnterOfferUser;
import org.sascha.qrbackend.EnterOfferUser.EnterOfferUserRepo;
import org.sascha.qrbackend.Offer.Offer;
import org.sascha.qrbackend.Offer.OfferRepo;
import org.sascha.qrbackend.User.DTO.QRIssuerDTORequest;
import org.sascha.qrbackend.User.DTO.QRIssuerResponse;
import org.sascha.qrbackend.User.DTO.QRRedeemCompanyUserResponse;
import org.sascha.qrbackend.User.DTO.QRRedeemPointsFromUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;

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
    EnterOfferUserRepo enterOfferUserRepo;




    @Autowired
    OfferRepo offerRepo;

    public QRIssuerResponse issue(String userId, String offerId) {

        var userUUID = UUID.fromString(userId);
        var offerUUID = UUID.fromString(offerId);

        var offer = offerRepo.findByOfferId((offerId))
                .orElseThrow(() -> new IllegalArgumentException("Offer not found"));

        int points = offer.getOfferPoints();
        String companyId = String.valueOf(offer.getCompany().getCompanyId());

        Instant expiresAt = Instant.now().plus(Duration.ofMinutes(2));

        String rawToken = UUID.randomUUID() + "." + offerUUID + "." + points + "." + userUUID + "."  + System.currentTimeMillis();
        String tokenHash = sha256(rawToken);

        QRTokenEntitiy qrToken = new QRTokenEntitiy(
                tokenHash,
                rawToken,
                userId,
                companyId,
                offerUUID.toString(),
                points,
                expiresAt,
                null);

        qrTokenRepo.save(qrToken);

        return new QRIssuerResponse(
                tokenHash,
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

    public QRRedeemPointsFromUserResponse qrRedeemPointsFromUser(String token, String companyId, String userId) {

        var companyUUID = UUID.fromString(companyId);

        QRTokenEntitiy addEntity = qrTokenRepo.findByTokenHash(token)
                .orElseThrow(() -> new RuntimeException("Ungültiger Token"));

        try {
            String raw = addEntity.getRawToken();
            String[] parts = raw.split("\\.");
            UUID qrId = UUID.fromString(parts[0]);
            String offerId = parts[1];
            Double userPoints = Double.parseDouble(parts[2]);
            UUID userUUID = UUID.fromString(parts[3]);
            long timestamp = Long.parseLong(parts[4]);

            System.out.println("Offer ID: " + offerId);

            // 1. Hole das Offer
            Offer offer = offerRepo.findByOfferId(offerId)
                    .orElseThrow(() -> new IllegalArgumentException("Offer not found"));

            // 2. ✅ Prüfe, ob das Offer zur scannenden Company gehört
            if (!offer.getCompany().getCompanyId().equals(companyUUID)) {
                return new QRRedeemPointsFromUserResponse(
                        false,
                        "Falsches Restaurant! Dieses Angebot gehört nicht zu Ihrem Restaurant.",
                        null,
                        null,
                        offer.getOfferName()
                );
            }

            // 3. Hole den EnterOfferUser Eintrag
            EnterOfferUser entry = enterOfferUserRepo.findByUserIdAndCompanyId(userUUID, companyUUID)
                    .orElseThrow(() -> new IllegalArgumentException("User hat dieses Angebot nicht"));

            // 4. Prüfe, ob schon eingelöst
            if (entry.getRedeemedOffers().contains(offerId)) {
                return new QRRedeemPointsFromUserResponse(
                        false,
                        "Angebot wurde schon eingelöst",
                        entry.getUserPoints(),
                        userUUID.toString(),
                        offer.getOfferName()
                );
            }

            // 5. Prüfe, ob genug Punkte vorhanden
            if (entry.getUserPoints() < userPoints) {
                return new QRRedeemPointsFromUserResponse(
                        false,
                        "Nicht genug Punkte! Benötigt: " + userPoints + ", Vorhanden: " + entry.getUserPoints(),
                        entry.getUserPoints(),
                        userUUID.toString(),
                        offer.getOfferName()
                );
            }

            // 6. Einlösen
            Double newUserPoints = entry.getUserPoints() - userPoints;
            Instant usedAt = Instant.now();

            entry.setUserPoints(newUserPoints);
            entry.getRedeemedOffers().add(offerId);

            addEntity.setUsedAt(usedAt);

            enterOfferUserRepo.save(entry);
            qrTokenRepo.save(addEntity);

            System.out.println("✅ Angebot eingelöst: " + offer.getOfferName());

            return new QRRedeemPointsFromUserResponse(
                    true,
                    "Angebot erfolgreich eingelöst!",
                    newUserPoints,
                    userUUID.toString(),
                    offer.getOfferName()
            );

        } catch (Exception e) {
            System.err.println("❌ Fehler beim Einlösen: " + e.getMessage());
            e.printStackTrace();
            return new QRRedeemPointsFromUserResponse(
                    false,
                    "Fehler: " + e.getMessage(),
                    null,
                    null,
                    null
            );
        }
    }

    public QRRedeemCompanyUserResponse qrStatus(String token, String userId, String companyId) {
        System.out.println("Token " + token);
        UUID userUUID = UUID.fromString(userId);
        UUID companyUUID = UUID.fromString(companyId);

        QRTokenEntitiy qrEntity = qrTokenRepo.findByTokenHash(token)
                .orElseThrow(() -> new IllegalArgumentException("Token nicht gefunden"));

        EnterOfferUser enter = enterOfferUserRepo
                .findByUserIdAndCompanyId(userUUID, companyUUID)
                .orElseThrow(() -> new IllegalArgumentException("EnterOfferUser nicht gefunden"));

        System.out.println("OfferId " + qrEntity.getOfferId());

        if (qrEntity.getUsedAt() == null) {
            return new QRRedeemCompanyUserResponse(
                    false,
                    false,
                    enter.getUserPoints(),
                    "QRCode noch nicht eingelöst!",
                    null
            );
        } else {


            qrTokenRepo.delete(qrEntity);
            return new QRRedeemCompanyUserResponse(
                    true,
                    true,
                    enter.getUserPoints(),
                    "Deine Punkte wurden erfolreich eingelöst",
                    qrEntity.getOfferId()

            );
        }
    }


}
