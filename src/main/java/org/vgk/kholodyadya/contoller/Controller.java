package org.vgk.kholodyadya.contoller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vgk.kholodyadya.entity.Product;
import org.vgk.kholodyadya.entity.User;
import org.vgk.kholodyadya.exceptions.InvalidQrException;
import org.vgk.kholodyadya.service.ProductService;
import org.vgk.kholodyadya.service.UserService;

import java.util.List;

@Service
@RestController
public class Controller {
    private final ProductService productService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    public Controller(ProductService productService, UserService userService, AuthenticationManager authenticationManager) {
        this.productService = productService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/products/{user_id}")
    public void addProducts(@PathVariable("user_id") int userId, @RequestBody List<Product> cart) {
        productService.addProductList(cart, userId);
    }

    @PostMapping("/products/qr/{user_id}")
    public ResponseEntity<String> addProductsWithinQr(@PathVariable("user_id") int userId, @RequestBody String qr) {
        try {
            productService.addProductsWithinQr(qr, userId);
            return new ResponseEntity<>("Products are successfully added", HttpStatus.OK);
        } catch (InvalidQrException exception) {
            return new ResponseEntity<> ("Given Qr is invalid", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/products/{user_id}")
    public ResponseEntity<List<Product>> getUserProducts(@PathVariable("user_id") int userId) {
        List<Product> products = productService.getUserProducts(userId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/auth/login")
    public void loginUser(@RequestBody User user) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        Object obj = auth.getPrincipal();
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