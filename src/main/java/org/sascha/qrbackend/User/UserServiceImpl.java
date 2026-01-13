package org.sascha.qrbackend.User;

import org.sascha.qrbackend.User.DTO.ChangePasswordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String firstname, String Lastname, String email, String password) {

        if (userRepo.existsByEmail(email)) {
            throw new RuntimeException("Username existiert bereits");
        }

        Integer userPoints = 0;
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(firstname, Lastname, email, hashedPassword);
        user.setUserPoints(userPoints);
        return userRepo.save(user);
    }

    public Optional<User> loginUser(String email, String password) {
        Optional<User> userOpt = userRepo.findByEmail(email);

        if(userOpt.isPresent()) {
            User user = userOpt.get();
            if(passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }


}
