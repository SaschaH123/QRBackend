package org.sascha.qrbackend.User.DTO;

public class QRReedemAddPointsFromCompanyResponse {

    private boolean success;
    private String message;
    private Double userPoints;

    public QRReedemAddPointsFromCompanyResponse() {}

    public QRReedemAddPointsFromCompanyResponse(boolean success, String message, Double userPoints) {
        this.success = success;
        this.message = message;
        this.userPoints = userPoints;
    }
    public Double getUserPoints() {return userPoints;}
    public boolean isSuccess() { return success; }
    public String getMessage() {return message;}

}
