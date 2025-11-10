package org.sascha.qrbackend.Authentication;

import org.sascha.qrbackend.User.DTO.LoginResponse;

public interface AuthService {


    LoginResponse loginUser(String email, String password);
}
