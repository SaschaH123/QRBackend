package org.sascha.qrbackend.User.DTO;

public class GetSingleCompanyListResponse {

    private boolean success;
    private String offerId;
    private String companyId;
    private String offerDesc;
    private String offerName;
    private Integer offerPoints;

    public GetSingleCompanyListResponse() {}

    public GetSingleCompanyListResponse(String offerId, boolean success, String companyId, String offerDesc, String offerName, Integer offerPoints) {
        this.offerId = offerId;
        this.success = success;
        this.companyId = companyId;
        this.offerDesc = offerDesc;
        this.offerName = offerName;
        this.offerPoints = offerPoints;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getOfferId() {
        return offerId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getOfferName() {
        return offerName;
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public Integer getOfferPoints() {
        return offerPoints;
    }

}
