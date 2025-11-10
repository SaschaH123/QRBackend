package org.sascha.qrbackend.User.DTO;

import java.util.UUID;

public class CreateOfferRequest {

    private UUID offerId;
    private String userId;
    private String offerName;
    private String offerDesc;
    private String offerStatus;
    private Integer offerPoints;

    public CreateOfferRequest() {}

    public CreateOfferRequest(String userId, String offerName, String offerDesc, Integer offerPoints, String offerStatus) {
        this.userId = userId;
        this.offerName = offerName;
        this.offerDesc = offerDesc;
        this.offerPoints = offerPoints;
        this.offerStatus = offerStatus;
    }

    public UUID getOfferId() {
        return offerId;
    }

    public void setOfferId(UUID offerId) {
        this.offerId = offerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public Integer getOfferPoints() {
        return offerPoints;
    }

    public void setOfferPoints(Integer offerPoints) {
        this.offerPoints = offerPoints;
    }
}
