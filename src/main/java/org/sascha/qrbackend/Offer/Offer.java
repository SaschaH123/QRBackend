package org.sascha.qrbackend.Offer;


import jakarta.persistence.*;
import org.sascha.qrbackend.Company.Company;
import org.sascha.qrbackend.User.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "offers")
public class Offer {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String offerId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private String offerName;

    @Column(nullable = false)
    private String offerDesc;

    @Column(nullable = false)
    private Integer offerPoints;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferStatus offerStatus;


    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Offer() {}

    public Offer(Company company, String offerName, String offerDesc, Integer offerPoints, OfferStatus offerStatus) {
        this.company = company;
        this.offerName = offerName;
        this.offerDesc = offerDesc;
        this.offerPoints = offerPoints;
        this.offerStatus = offerStatus;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public OfferStatus getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(OfferStatus offerStatus) {
        this.offerStatus = offerStatus;
    }

    public Integer getOfferPoints() {
        return offerPoints;
    }

    public void setOfferPoints(Integer points) {
        this.offerPoints = points;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
