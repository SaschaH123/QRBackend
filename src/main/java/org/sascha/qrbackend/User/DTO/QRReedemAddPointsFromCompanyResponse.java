package org.sascha.qrbackend.User.DTO;

public class QRReedemAddPointsFromCompanyResponse {

    public boolean success;
    public String message;
    public Double userPoints;
    public Double newUserPoints;
    public String companyId;
    public String companyName;

    public QRReedemAddPointsFromCompanyResponse() {}

    public QRReedemAddPointsFromCompanyResponse(boolean success, String message, Double userPoints, Double newUserPoints, String companyId, String companyName) {
        this.success = success;
        this.message = message;
        this.userPoints = userPoints;
        this.newUserPoints = newUserPoints;
        this.companyId = companyId;
        this.companyName = companyName;
    }
    public Double getUserPoints() {return userPoints;}
    public boolean isSuccess() { return success; }
    public String getMessage() {return message;}
    public String getCompanyId() {return companyId;}
    public String getCompanyName(){return companyName;}
    public Double getNewUserPoints() {
        return newUserPoints;
    }

}
