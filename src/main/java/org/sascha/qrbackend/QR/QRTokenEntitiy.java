package org.sascha.qrbackend.QR;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "qr_tokens")
public class QRTokenEntitiy {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        @Column(nullable = false, unique = true, length = 60)
        private String tokenHash;

        @Column(nullable = false)
        private String userId;

        @Column(nullable = false)
        private String offerId;

        @Column(nullable = false)
        private String companyId;

        @Column(nullable = false)
        private Integer points;

        @Column(nullable = false)
        private Instant expiresAt;

        private Instant usedAt;

        public QRTokenEntitiy() {}

    public QRTokenEntitiy(String tokenHash, String userId, String companyId,
                          String offerId, Integer points, Instant expiresAt, Instant usedAt) {

        this.tokenHash = tokenHash;
        this.userId = userId;
        this.companyId = companyId;
        this.offerId = offerId;
        this.points = points;
        this.expiresAt = expiresAt;
        this.usedAt = usedAt;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTokenHash() {
        return tokenHash;
    }

    public void setTokenHash(String tokenHash) {
        this.tokenHash = tokenHash;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Instant getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(Instant usedAt) {
        this.usedAt = usedAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
}



