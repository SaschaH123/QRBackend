package org.sascha.qrbackend.User.DTO;

public class QRRedeemPointsFromUserRequest {

    private String tokenhash;

    QRRedeemPointsFromUserRequest() {}

    public QRRedeemPointsFromUserRequest(String tokenhash) {
        this.tokenhash = tokenhash;
    }

    public String getTokenhash() {
        return tokenhash;
    }

    public void setTokenhash(String tokenhash) {
        this.tokenhash = tokenhash;
    }

}

