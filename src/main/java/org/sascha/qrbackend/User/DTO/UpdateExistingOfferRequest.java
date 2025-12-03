package org.sascha.qrbackend.User.DTO;

public class UpdateExistingOfferRequest {
    private String offerName;
    private String offerDesc;
    private int offerPoints;

    public UpdateExistingOfferRequest() {
    }

    public UpdateExistingOfferRequest(String offerName, String offerDesc, int offerPoints) {
        this.offerName = offerName;
        this.offerDesc = offerDesc;
        this.offerPoints = offerPoints;
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

    public int getOfferPoints() {
        return offerPoints;
    }

    public void setOfferPoints(int offerPoints) {
        this.offerPoints = offerPoints;
    }
}
