package org.sascha.qrbackend.User.DTO;

public class GetCompanyListResponse {

    public String companyId;
    public String companyName;
    public String companyDesc;
    public String imageUrl;

    public GetCompanyListResponse() {
    }

    public GetCompanyListResponse(String companyId, String companyName, String companyDesc, String imageUrl) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyDesc = companyDesc;
        this.imageUrl = imageUrl;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyDesc() {
        return companyDesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
