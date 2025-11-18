package org.sascha.qrbackend.User.DTO;

import java.time.Instant;

public class QRAddPointsIssuerResponse {


        private boolean success;
        private String token;
        private String message;
        private Instant expiresAt;
        private Integer userPoints;

        public QRAddPointsIssuerResponse(String token, boolean success, String message,
                                         Instant expiresAt, Integer userPoints) {
            this.token = token;
            this.success = success;
            this.message = message;
            this.expiresAt = expiresAt;
            this.userPoints = userPoints;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() {return message;}
        public String getToken() { return token; }
        public Instant getExpiresAt() { return expiresAt; }
        public Integer getUserPoints() {return userPoints;}
    }

