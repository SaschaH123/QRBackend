package org.sascha.qrbackend.User.DTO;

public class QRRedeemUserCompanyResponse {

    public boolean success;
    public boolean redeemed;
    public String message;

    public QRRedeemUserCompanyResponse () {}

    public QRRedeemUserCompanyResponse (boolean success, boolean redeemed, String message) {
        this.success = success;
        this.redeemed = redeemed;
        this.message = message;
    }

    public boolean isSuccess() {return success;}

    public boolean isRedeemed() {return redeemed;}

    public String getMessage() {return message;}
}
