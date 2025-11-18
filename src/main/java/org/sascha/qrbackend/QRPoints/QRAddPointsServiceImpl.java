package org.sascha.qrbackend.QRPoints;

import org.sascha.qrbackend.Company.CompanyRepo;
import org.sascha.qrbackend.User.DTO.QRAddPointsIssuerResponse;
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
    CompanyRepo companyRepo;

    public QRAddPointsIssuerResponse qrAddPointsissue(String companyId, Double userPrice) {

        var companyUUID = UUID.fromString(companyId);

        var company = companyRepo.findByCompanyId(companyUUID)
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));

        int userPoints = Math.toIntExact(Math.round(userPrice * 1.27));

        Instant expiresAt = Instant.now().plus(Duration.ofMinutes(2));

        String rawToken = UUID.randomUUID() + "." + companyId + "." + userPoints + "." + System.currentTimeMillis();
        String tokenHash = sha256(rawToken);

        QRAddPointsEntity qrAddPoint = new QRAddPointsEntity(
                tokenHash,
                companyUUID.toString(),
                userPoints,
                expiresAt,
                null);

        qrAddPointsRepo.save(qrAddPoint);

        return new QRAddPointsIssuerResponse(
                rawToken,
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

}
