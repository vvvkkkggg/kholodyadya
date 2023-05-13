package org.vgk.kholodyadya.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.vgk.kholodyadya.entity.User;
import org.vgk.kholodyadya.repository.UserRepository;
import org.vgk.kholodyadya.service.AuthService;
import org.vgk.kholodyadya.service.UserService;

@Service
@RestController
public class UserController {

    private final UserRepository userRepository;
    private final AuthService authService;

    public UserController(UserRepository userRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public void loginUser(@RequestBody User user) {
        authService.login(user.getUsername(), user.getPassword());
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        boolean exists = userRepository.existsUserByUsername(user.getUsername());
        if (exists) {
            return new ResponseEntity<> ("User already exists", HttpStatus.BAD_REQUEST);
        }
        userRepository.save(user);
        return new ResponseEntity<> ("Ok", HttpStatus.OK);
    }

}