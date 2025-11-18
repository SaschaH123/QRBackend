package org.sascha.qrbackend.QRPoints;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "qr_addpoints")
public class QRAddPointsEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String id;

        @Column(nullable = false, unique = true, length = 60)
        private String tokenHash;

        @Column(nullable = false)
        private String companyId;

        @Column(nullable = false)
        private Integer points;

        @Column(nullable = false)
        private Instant expiresAt;

        private Instant usedAt;

        public QRAddPointsEntity() {}

    public QRAddPointsEntity(String tokenHash, String companyId, Integer points, Instant expiresAt, Instant usedAt) {

        this.tokenHash = tokenHash;
        this.companyId = companyId;
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



