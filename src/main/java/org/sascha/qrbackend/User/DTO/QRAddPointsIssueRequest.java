package org.sascha.qrbackend.User.DTO;

public class QRAddPointsIssueRequest {

    private String companyId;
    private Double userPrice;

    public QRAddPointsIssueRequest() {}

    public QRAddPointsIssueRequest(String companyId, Double userPrice) {
        this.companyId = companyId;
        this.userPrice = userPrice;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public Double getUserPrice() {
        return userPrice;
    }

    public void setUserPrice(Double userPrice) {
        this.userPrice = userPrice;
    }
}

