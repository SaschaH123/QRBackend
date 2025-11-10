package org.sascha.qrbackend.User.DTO;

public class CreateOfferResponse {

    private boolean success;
    private String offerId;
    private String userId;
    private String offerDesc;
    private String offerName;
    private Integer offerPoints;
    private String offerStatus;
    private String createdAt;

    public CreateOfferResponse(boolean success, String offerId, String userId, String offerName, String offerDesc, Integer offerPoints, String offerStatus, String createdAt) {
        this.success = success;
        this.offerId = offerId;
        this.userId = userId;
        this.offerDesc = offerDesc;
        this.offerName = offerName;
        this.offerPoints = offerPoints;
        this.offerStatus = offerStatus;
        this.createdAt = createdAt;
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
}



