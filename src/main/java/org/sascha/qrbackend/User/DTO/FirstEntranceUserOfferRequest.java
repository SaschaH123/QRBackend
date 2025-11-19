package org.sascha.qrbackend.User.DTO;

public class FirstEntranceUserOfferRequest {

    private String companyId;

    public FirstEntranceUserOfferRequest() {}

    public FirstEntranceUserOfferRequest(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() { return companyId; }
    public void setCompanyId(String companyId) { this.companyId = companyId; }
}

