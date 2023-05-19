package org.vgk.kholodyadya.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.vgk.kholodyadya.entity.User;
import org.vgk.kholodyadya.exceptions.UserAlreadyExistsException;
import org.vgk.kholodyadya.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean checkUserExists(int userId) {
        return userRepository.existsUserById(userId);
    }

    public boolean checkUserExists(String username) {
        return userRepository.existsUserByUsername(username);
    }

    public void registerUser(User user) {
        if (checkUserExists(user.getUsername())) {
            throw new UserAlreadyExistsException("user " + user.getUsername() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
