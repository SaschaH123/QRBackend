package org.sascha.qrbackend.Authentication;


import org.sascha.qrbackend.User.DTO.LoginRequest;
import org.sascha.qrbackend.User.DTO.LoginResponse;
import org.sascha.qrbackend.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        System.out.println("Login request received");
        return authService.loginUser(
                request.getEmail(), request.getPassword());

    }
}
