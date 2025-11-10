package org.sascha.qrbackend.User;

import java.util.Optional;

public interface UserService {
    User registerUser(String firstName, String lastName, String email, String password);
}
