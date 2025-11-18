package org.sascha.qrbackend.User.DTO;

import java.time.Duration;
import java.time.Instant;

public class QRIssuerResponse {

    private boolean success;
    private String token;
    private String message;
    private Instant expiresAt;


    public QRIssuerResponse() {}

    public QRIssuerResponse(String token, boolean success, String message, Instant expiresAt) {
        this.token = token;
        this.success = success;
        this.message = message;
        this.expiresAt = expiresAt;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() {return message;}
    public String getToken() { return token; }
    public Instant getExpiresAt() { return expiresAt; }
}

