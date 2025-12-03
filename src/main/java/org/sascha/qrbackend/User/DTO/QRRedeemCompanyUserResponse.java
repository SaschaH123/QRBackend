package org.sascha.qrbackend.User.DTO;

public class QRRedeemCompanyUserResponse {

    public boolean success;
    public boolean redeemed;
    public Double userPoints;
    public String message;
    public String offerId;

    public QRRedeemCompanyUserResponse() {}

    public QRRedeemCompanyUserResponse(boolean success, boolean redeemed, Double userPoints, String message, String offerId) {
        this.success = success;
        this.redeemed = redeemed;
        this.userPoints = userPoints;
        this.message = message;
        this.offerId = offerId;
    }

    public boolean isSuccess() {return success;}

    public Double getUserPoints() {return userPoints;}

    public boolean isRedeemed() {return redeemed;}

    public String getMessage() {return message;}

    public String getOfferId() {
        return offerId;
    }
}
