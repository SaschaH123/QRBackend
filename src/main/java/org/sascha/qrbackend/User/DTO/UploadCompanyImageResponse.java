package org.sascha.qrbackend.User.DTO;

public class UploadCompanyImageResponse {

    private boolean success;
    private String message;
    private String imageUrl;

    public UploadCompanyImageResponse() {
    }

    public UploadCompanyImageResponse(String imageUrl, String message, boolean success) {
        this.imageUrl = imageUrl;
        this.message = message;
        this.success = success;
    }

    // Getter & Setter
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
