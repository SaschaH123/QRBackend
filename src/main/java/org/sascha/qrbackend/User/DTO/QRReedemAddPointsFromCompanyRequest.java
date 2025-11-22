package org.sascha.qrbackend.User.DTO;

public class QRReedemAddPointsFromCompanyRequest {

    private String tokenhash;

    QRReedemAddPointsFromCompanyRequest() {}

    public QRReedemAddPointsFromCompanyRequest(String tokenhash) {
        this.tokenhash = tokenhash;
    }

    public String getTokenhash() {
        return tokenhash;
    }

    public void setTokenhash(String tokenhash) {
        this.tokenhash = tokenhash;
    }

}
