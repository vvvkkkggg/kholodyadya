package org.vgk.kholodyadya.contoller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vgk.kholodyadya.entity.User;
import org.vgk.kholodyadya.exceptions.InvalidQrException;
import org.vgk.kholodyadya.exceptions.NonexistentUserIdException;
import org.vgk.kholodyadya.repository.ProductRepository;
import org.vgk.kholodyadya.repository.UserRepository;
import org.vgk.kholodyadya.service.ProductService;
import org.vgk.kholodyadya.service.UserService;
import org.vgk.kholodyadya.service.ValidateQr;

import java.util.List;

@Service
@RestController
public class Controller {
    private ProductService productService;
    private UserService userService;

    public Controller(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @Getter
    @Setter
    private static class UserProduct {
        private String name;
        private String shelfLife;

    }

    @Getter
    @Setter
    private static class UserProductsList {
        private List<UserProduct> productsList;

    }

    @PostMapping("/products/{user_id}")
    public void addProducts(@PathVariable("user_id") int userId, @RequestBody UserProductsList cart) {
//        User user = userRepository.findById(user_id)
//            .orElseThrow(() -> new NonexistentUserIdException("non existent user id passed"));
    }

    @PostMapping("/products/qr/{user_id}")
    public ResponseEntity<String> addProductsWithinQr(@PathVariable("user_id") int userId, @RequestBody String qr) {
        try {
            productService.addProductsWithinQr(qr, userId);
            return new ResponseEntity<>("Products are successfully added", HttpStatus.OK);
        } catch (InvalidQrException exception) {
            return new ResponseEntity<> ("Given Qr is invalid", HttpStatus.BAD_REQUEST);
        }

//        boolean isCorrect = ValidateQr.validateQr(qr);
//        if (!isCorrect) {
//            throw new InvalidQrException("qr format is invalid");
//        }
    }

    @GetMapping("/products/{user_id}")
    public String getUserProducts(@PathVariable("user_id") int userId) {
        boolean isCorrect = ValidateQr.validateQr("fwfwe");
        return "OK";
    }

    @PostMapping("/auth/login")
    public void loginUser(@RequestBody User user) {
        // authService.login(user.getUsername(), user.getPassword());
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@Validated @RequestBody User user) {
        try {
            userService.registerUser(user);
        } catch (Throwable e) {
            return new ResponseEntity<> ("Unable to create user", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<> ("Ok", HttpStatus.OK);
    }

}