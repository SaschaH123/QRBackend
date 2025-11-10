package org.sascha.qrbackend.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(String firstname, String Lastname, String email, String password) {

        if (userRepo.existsByEmail(email)) {
            throw new RuntimeException("Username existiert bereits");
        }

        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(firstname, Lastname, email, hashedPassword);
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
