package org.sascha.qrbackend.User;


import org.sascha.qrbackend.User.DTO.ChangePasswordRequest;
import org.sascha.qrbackend.User.DTO.ChangePasswordResponse;
import org.sascha.qrbackend.User.DTO.LoginRequest;
import org.sascha.qrbackend.User.DTO.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserService userService;

    @Autowired
    private UserRepo userRepo;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(request.getFirstname(), request.getLastname(), request.getEmail(), request.getPassword());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody ChangePasswordRequest request, Authentication authentication) {
        String userId = authentication.getName();

        UUID userUUID = UUID.fromString(userId);

        System.out.println("Altes Password " + request.getOldPassword() + " Neues Passwort " + request.newPassword) ;

        Optional<User> userOpt = userRepo.findById(userUUID);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
                String hashedPassword = passwordEncoder.encode(request.getNewPassword());
                System.out.println("Hashed PAssword " + hashedPassword);
                user.setPassword(hashedPassword);
                userRepo.save(user);
                return ResponseEntity.ok()
                        .body(new ChangePasswordResponse(
                        true,
                        "Password erfolgreich geändert"
                        ));
            } else {
                return ResponseEntity.ok()
                        .body(new ChangePasswordResponse(
                        false,
                        "Altes password stimmt nicht überein!"
                ));
            }
        } else {
            return ResponseEntity.ok()
                    .body(new ChangePasswordResponse(
                            false,
                            "User nicht gefunden"
                    ));
        }
    }

}
