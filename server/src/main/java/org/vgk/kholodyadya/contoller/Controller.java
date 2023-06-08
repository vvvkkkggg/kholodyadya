package org.vgk.kholodyadya.contoller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vgk.kholodyadya.config.AuthUserDetailsService;
import org.vgk.kholodyadya.config.JwtTokenUtil;
import org.vgk.kholodyadya.entity.Product;
import org.vgk.kholodyadya.entity.User;
import org.vgk.kholodyadya.exceptions.InvalidQrException;
import org.vgk.kholodyadya.exceptions.NonExistentProduct;
import org.vgk.kholodyadya.exceptions.UserAlreadyExistsException;
import org.vgk.kholodyadya.service.ProductService;
import org.vgk.kholodyadya.service.UserService;

import java.util.List;

@Service
@RestController
public class Controller {
    private final ProductService productService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AuthUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;


    public Controller(ProductService productService, UserService userService, AuthenticationManager authenticationManager, AuthUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.productService = productService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/products")
    @Operation(summary = "method to add list of products to user's cart")
    public void addProducts(@RequestBody List<Product> cart) {
        int userId = userService.getAuthenticatedUserId();
        productService.addProductList(cart, userId);
    }

    @PostMapping("/products/qr")
    @Operation(summary = "method to add list of products to user's cart within QR code")
    public ResponseEntity<String> addProductsWithinQr(@RequestBody String qr) {
        try {
            int userId = userService.getAuthenticatedUserId();
            productService.addProductsWithinQr(qr, userId);
            return new ResponseEntity<>("Products are successfully added", HttpStatus.OK);
        } catch (InvalidQrException exception) {
            return new ResponseEntity<> ("Given Qr is invalid", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/products")
    @Operation(summary = "method to get list of user's products")
    public ResponseEntity<List<Product>> getUserProducts() {
        int userId = userService.getAuthenticatedUserId();

        List<Product> products = productService.getUserProducts(userId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @DeleteMapping("/products")
    @Operation(summary = "method to delete product from cart")
    public ResponseEntity<String> deleteProduct(@RequestBody Product product) {
        int userId = userService.getAuthenticatedUserId();
        try {
            productService.deleteProduct(product, userId);
            return ResponseEntity.ok().build();
        } catch (NonExistentProduct e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/auth/login")
    @Operation(summary = "method to log in")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            UserDetails result = userDetailsService.loadUserByUsername(user.getUsername());

            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            jwtTokenUtil.generateToken(result)
                    ).build();
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/auth/register")
    @Operation(summary = "method to register in service")
    public ResponseEntity<User> registerUser(@Validated @RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return new ResponseEntity<> (registeredUser, HttpStatus.OK);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<> (User.builder().build(), HttpStatus.BAD_REQUEST);
        }
    }
}