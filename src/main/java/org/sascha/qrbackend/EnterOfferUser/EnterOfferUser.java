package org.sascha.qrbackend.EnterOfferUser;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "enter_offer_user")
public class EnterOfferUser {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String enterOfferUserId;

    private UUID userId;
    private UUID companyId;



    private Double userPoints;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> redeemedOffers = new HashSet<>();

    public EnterOfferUser() {}

    public EnterOfferUser(UUID userId, UUID companyId, Double userPoints) {
        this.userId = userId;
        this.companyId = companyId;
        this.userPoints = userPoints;
    }

    public String getEnterOfferUserId() {
        return enterOfferUserId;
    }

    public void setEnterOfferUserId(String enterOfferUserId) {
        this.enterOfferUserId = enterOfferUserId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public Double getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(Double userPoints) {
        this.userPoints = userPoints;
    }

    public Set<String> getRedeemedOffers() {return redeemedOffers;}

    public void setRedeemedOffers(Set<String> redeemedOffers) {this.redeemedOffers = redeemedOffers;}
}

