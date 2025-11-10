package org.sascha.qrbackend.User;


import org.sascha.qrbackend.User.DTO.LoginRequest;
import org.sascha.qrbackend.User.DTO.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request.getFirstname(), request.getLastname(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok(user);
    }

}
