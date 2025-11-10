package org.sascha.qrbackend.User.DTO;

import java.util.UUID;

public class LoginResponse {

    private boolean success;
    private String message;
    private String userId;
    private String type;
    private String name;
    private String token;

    public LoginResponse(boolean success, String message, String userId, String type, String name, String token) {
        this.success = success;
        this.message = message;
        this.userId = userId;
        this.type = type;
        this.name = name;
        this.token = token;
    }

    public boolean isSuccess() {return success;}
    public String getMessage() {return message;}
    public String getUserId() {return userId;}
    public String getType() {return type;}
    public String getName() {return name;}
    public String getToken() {return token;}
}
