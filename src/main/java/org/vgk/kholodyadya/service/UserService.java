package org.vgk.kholodyadya.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.vgk.kholodyadya.entity.User;
import org.vgk.kholodyadya.exceptions.UserAlreadyExistsException;
import org.vgk.kholodyadya.repository.UserRepository;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
