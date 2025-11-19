package org.sascha.qrbackend.User.DTO;

public class EnterOfferUserResponse {

        private boolean success;
        private String message;
        private Double userPoints;

        public EnterOfferUserResponse(boolean success, String message, Double userPoints) {
            this.success = success;
            this.message = message;
            this.userPoints = userPoints;
        }

        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public Double getUserPoints() {return userPoints;}
}
