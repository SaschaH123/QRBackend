package org.sascha.qrbackend.QRPoints;

import org.sascha.qrbackend.Company.Company;
import org.sascha.qrbackend.Company.CompanyRepo;
import org.sascha.qrbackend.Employee.Employee;
import org.sascha.qrbackend.Employee.EmployeeRepo;
import org.sascha.qrbackend.EnterOfferUser.EnterOfferUser;
import org.sascha.qrbackend.EnterOfferUser.EnterOfferUserRepo;
import org.sascha.qrbackend.Offer.Offer;
import org.sascha.qrbackend.Offer.OfferRepo;
import org.sascha.qrbackend.User.DTO.QRAddPointsIssuerResponse;
import org.sascha.qrbackend.User.DTO.QRRedeemCompanyUserResponse;
import org.sascha.qrbackend.User.DTO.QRRedeemUserCompanyResponse;
import org.sascha.qrbackend.User.DTO.QRReedemAddPointsFromCompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class QRAddPointsServiceImpl implements QRAddPointsService {

    @Autowired
    QRAddPointsRepo qrAddPointsRepo;

    @Autowired
    EnterOfferUserRepo enterOfferUserRepo;

    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    OfferRepo offerRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    public QRAddPointsIssuerResponse qrAddPointsissue(String userId, Double userPrice) {

        var userUUID = UUID.fromString(userId);
        System.out.println("🔍 Suche User (Company oder Employee): " + userUUID);

        // ✅ Erst in Company suchen
        Optional<Company> company = companyRepo.findByCompanyId(userUUID);
        if (company.isPresent()) {
            System.out.println("✅ Company gefunden: " + company.get().getCompanyName());
            return createQRToken(company.get().getCompanyId(), company.get().getCompanyName(), userPrice);
        }

        // ✅ Wenn nicht gefunden, in Employee suchen
        Optional<Employee> employee = employeeRepo.findById(userUUID);
        if (employee.isPresent()) {
            System.out.println("✅ Employee gefunden: " + employee.get().getEmployeeEmail());
            return createQRToken(
                    employee.get().getEmployeeId(),
                    employee.get().getCompany().getCompanyName(),
                    userPrice
            );
        }

        throw new RuntimeException("❌ User nicht gefunden!");
    }

    private QRAddPointsIssuerResponse createQRToken(UUID userId, String companyName, Double userPrice) {
        int userPoints = Math.toIntExact((long) Math.ceil(userPrice / 3.0));
        Instant expiresAt = Instant.now().plus(Duration.ofMinutes(10));

        // ✅ ENTWEDER Company ODER Employee
        Optional<Company> company = companyRepo.findByCompanyId(userId);
        Optional<Employee> employee = employeeRepo.findById(userId);

        UUID companyId = null;

        if(company.isPresent()) {
            // ✅ Company loggt ein
            companyId = company.get().getCompanyId();
            System.out.println("✅ Company: " + company.get().getCompanyName());
        }
        else if(employee.isPresent()) {
            // ✅ Employee loggt ein
            Employee e = employee.get();
            companyId = e.getCompany().getCompanyId();
            System.out.println("✅ Employee: " + e.getEmployeeEmail());
        }
        else {
            throw new RuntimeException("❌ User/Company nicht gefunden!");
        }

        String rawToken = UUID.randomUUID() + "." + companyId + "." + userPoints + "." + System.currentTimeMillis();
        String tokenHash = sha256(rawToken);

        QRAddPointsEntity qrAddPoint = new QRAddPointsEntity(
                tokenHash,
                rawToken,
                userId.toString(),
                companyId.toString(),
                userPoints,
                expiresAt,
                null);

        qrAddPointsRepo.save(qrAddPoint);

        return new QRAddPointsIssuerResponse(
                tokenHash,
                true,
                companyName,
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

        System.out.println("Token: " + token);
        var userUUID = UUID.fromString(userId);

        QRAddPointsEntity addEntity = qrAddPointsRepo
                .findByTokenHash(token)
                .orElseThrow(() -> new RuntimeException("❌ Ungültiger Token"));

        try {
            String raw = addEntity.getRawToken();
            String[] parts = raw.split("\\.");

            UUID qrId = UUID.fromString(parts[0]);
            UUID companyUUID = UUID.fromString(parts[1]);  // ← parts[1]!
            double addedUserPoints = Double.parseDouble(parts[2]);  // ← parts[2]!
            long timestamp = Long.parseLong(parts[3]);  // ← parts[3]!
            Instant expiresAt = Instant.ofEpochMilli(timestamp);

            // ✅ Token ABGELAUFEN wenn jetzt NACH expiresAt ist!
            /*if (Instant.now().isAfter(expiresAt)) {  // ← isAfter, nicht isBefore!
                return new QRReedemAddPointsFromCompanyResponse(
                        false,
                        "❌ Token abgelaufen",
                        0.0,
                        null,
                        null
                );
            }*/

            EnterOfferUser entry = enterOfferUserRepo
                    .findByUserIdAndCompanyId(userUUID, companyUUID)
                    .orElseThrow(() -> new RuntimeException("Kein Eintrag für User/Company"));

            var company = companyRepo.findByCompanyId(companyUUID)
                    .orElseThrow(() -> new RuntimeException("Keine Company gefunden"));

            double newPoints = entry.getUserPoints() + addedUserPoints;
            System.out.println("newUserPoints " + newPoints);
            entry.setUserPoints(newPoints);
            addEntity.setUsedAt(Instant.now());  // ✅ Token als verwendet markieren!

            enterOfferUserRepo.save(entry);
            qrAddPointsRepo.save(addEntity);

            return new QRReedemAddPointsFromCompanyResponse(
                    true,
                    "✅ Punkte hinzugefügt",
                    addedUserPoints,
                    newPoints,
                    companyUUID.toString(),
                    company.getCompanyName()
            );

        } catch (Exception e) {
            System.err.println("❌ Fehler: " + e.getMessage());
            e.printStackTrace();
            return new QRReedemAddPointsFromCompanyResponse(
                    false,
                    "Fehler: " + e.getMessage(),
                    null,
                    null,
                    null,
                    null
            );
        }
    }

    @Override
    public QRRedeemUserCompanyResponse qrStatus(String tokenHash, String userId) {
        System.out.println("Tokenhash " + tokenHash);

        Optional<QRAddPointsEntity> qrEntity = qrAddPointsRepo.findByTokenHash(tokenHash);
        if(qrEntity.isPresent()) {
            QRAddPointsEntity add = qrEntity.get();
            if(add.getUsedAt() != null) {
                qrAddPointsRepo.delete(add);
                return new QRRedeemUserCompanyResponse(
                        true,
                        true,
                        "Punkte hinzugefügt!"

                );
            } else {
                return new QRRedeemUserCompanyResponse(
                        false,
                        false,
                        ""
                );
            }


        } else {
            return new QRRedeemUserCompanyResponse(
                    false,
                    false,
                    "QR-Code Scan fehlgeschlagen bitte erneut versuchen!"
            );
        }

    }

}
