package org.sascha.qrbackend.User.DTO;

public class QRRedeemPointsFromUserResponse {

    public boolean success;
    public String message;
    public Double newUserPoints;
    public String userId;
    public String offerName;

    public QRRedeemPointsFromUserResponse() {}


    public QRRedeemPointsFromUserResponse(boolean success, String message, Double newUserPoints, String userId, String offerName) {
        this.success = success;
        this.message = message;
        this.newUserPoints = newUserPoints;
        this.userId = userId;
        this.offerName = offerName;
    }

    public String getMessage() {return message;}

    public Double getNewUserPoints() {return newUserPoints;}

    public boolean isSuccess() {return success;}

    public String getUserId() {return userId;}

    public String getOfferName(){return offerName;}
}