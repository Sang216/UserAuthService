package com.sang.userauthservice.service;

import com.sang.userauthservice.model.Role;
import com.sang.userauthservice.model.User;
import com.sang.userauthservice.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signUp(String email, String password) {

        if (userRepository.existsByEmail(email)) {
            return null;
        }

        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }
}
