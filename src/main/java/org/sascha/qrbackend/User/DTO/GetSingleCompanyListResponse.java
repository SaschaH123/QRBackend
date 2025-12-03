package org.sascha.qrbackend.User.DTO;

import java.util.HashSet;
import java.util.Set;

public class GetSingleCompanyListResponse {

    private boolean success;
    private String offerId;
    private String companyId;
    private String offerDesc;
    private String offerName;
    private Integer offerPoints;
    private boolean redeemed;
    private String imageUrl;

    public GetSingleCompanyListResponse() {
    }

    public GetSingleCompanyListResponse(String offerId, boolean success, String companyId, String offerDesc, String offerName, Integer offerPoints, boolean redeemed, String imageUrl) {
        this.offerId = offerId;
        this.success = success;
        this.companyId = companyId;
        this.offerDesc = offerDesc;
        this.offerName = offerName;
        this.offerPoints = offerPoints;
        this.redeemed = redeemed;
        this.imageUrl = imageUrl;
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

    public boolean isRedeemed() {
        return redeemed;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
