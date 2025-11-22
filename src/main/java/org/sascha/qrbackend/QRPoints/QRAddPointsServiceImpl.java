package org.sascha.qrbackend.QRPoints;

import org.sascha.qrbackend.Company.CompanyRepo;
import org.sascha.qrbackend.EnterOfferUser.EnterOfferUser;
import org.sascha.qrbackend.EnterOfferUser.EnterOfferUserRepo;
import org.sascha.qrbackend.User.DTO.QRAddPointsIssuerResponse;
import org.sascha.qrbackend.User.DTO.QRReedemAddPointsFromCompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.UUID;

@Service
public class QRAddPointsServiceImpl implements QRAddPointsService {

    @Autowired
    QRAddPointsRepo qrAddPointsRepo;

    @Autowired
    EnterOfferUserRepo enterOfferUserRepo;

    @Autowired
    CompanyRepo companyRepo;

    public QRAddPointsIssuerResponse qrAddPointsissue(String companyId, Double userPrice) {

        var companyUUID = UUID.fromString(companyId);

        var company = companyRepo.findByCompanyId(companyUUID)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        int userPoints = Math.toIntExact(Math.round(userPrice * 0.50));

        Instant expiresAt = Instant.now().plus(Duration.ofMinutes(10));

        String rawToken = UUID.randomUUID() + "." + companyId + "." + userPoints + "." + System.currentTimeMillis();
        String tokenHash = sha256(rawToken);

        QRAddPointsEntity qrAddPoint = new QRAddPointsEntity(
                tokenHash,
                rawToken,
                companyUUID.toString(),
                userPoints,
                expiresAt,
                null);

        qrAddPointsRepo.save(qrAddPoint);

        return new QRAddPointsIssuerResponse(
                tokenHash,
                true,
                company.getCompanyName(),
                expiresAt,
                userPoints
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

    public QRReedemAddPointsFromCompanyResponse qrRedeemScanFromUser(String token, String userId) {

        System.out.println(token);

        var userUUID = UUID.fromString(userId);

        QRAddPointsEntity addEntity = qrAddPointsRepo.
                findByTokenHash(token)
                .orElseThrow(()->new RuntimeException("Ungültiger Token"));

        try{
            String raw = addEntity.getRawToken();
            String[] parts = raw.split("\\.");
            UUID qrId = UUID.fromString(parts[0]);
            UUID companyUUID = UUID.fromString(parts[1]);
            double addedUserPoints = Double.parseDouble(parts[2]);
            long timestamp = Long.parseLong(parts[3]);
            Instant expiresAt = Instant.ofEpochMilli(timestamp);

            /*if (Instant.now().isAfter(expiredAt)) {
                return new QRReedemAddPointsFromCompanyResponse(
                        false,
                        "Token abgelaufen",
                        0.0
                );
            }*/

        EnterOfferUser entry = enterOfferUserRepo
                .findByUserIdAndCompanyId(userUUID, companyUUID)
                .orElseThrow(()->new RuntimeException("Kein Eintrag für User/Company"));

            double newPoints = entry.getUserPoints() + addedUserPoints;
            entry.setUserPoints(newPoints);



            enterOfferUserRepo.save(entry);

            return new QRReedemAddPointsFromCompanyResponse(
                    true,
                    "Punkte hinzugefügt",
                    newPoints


            );

        } catch (Exception e) {
            return new QRReedemAddPointsFromCompanyResponse(
                    false,
                    "Fehler: " + e.getMessage(),
                    0.0
            );
        }
    }




}
