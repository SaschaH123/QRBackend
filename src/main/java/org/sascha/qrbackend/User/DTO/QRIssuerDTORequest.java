package org.sascha.qrbackend.User.DTO;

public class QRIssuerDTORequest {

    private String offerId;

    public QRIssuerDTORequest() {}

    public QRIssuerDTORequest(String offerId) {
        this.offerId =offerId;
    }



    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }
}
