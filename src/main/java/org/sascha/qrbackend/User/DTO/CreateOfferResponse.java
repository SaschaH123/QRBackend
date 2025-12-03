package org.sascha.qrbackend.User.DTO;

public class CreateOfferResponse {

    public boolean success;
    public String offerId;
    public String userId;
    public String offerName;
    public String offerDesc;
    public Integer offerPoints;
    public String offerStatus;
    public String createdAt;
    public String imageUrl;

    public CreateOfferResponse() {}

    public CreateOfferResponse(boolean success, String offerId, String userId, String offerName, String offerDesc, Integer offerPoints, String offerStatus, String createdAt, String imageUrl) {
        this.success = success;
        this.offerId = offerId;
        this.userId = userId;
        this.offerName = offerName;
        this.offerDesc = offerDesc;
        this.offerPoints = offerPoints;
        this.offerStatus = offerStatus;
        this.createdAt = createdAt;
        this.imageUrl = imageUrl;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
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

    public void setOfferDesc(String offerDesc) {
        this.offerDesc = offerDesc;
    }

    public Integer getOfferPoints() {
        return offerPoints;
    }

    public void setOfferPoints(Integer offerPoints) {
        this.offerPoints = offerPoints;
    }

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageUrl() {return imageUrl;}
}



