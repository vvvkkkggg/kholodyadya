package org.vgk.kholodyadya.contoller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.vgk.kholodyadya.config.AuthUserDetailsService;
import org.vgk.kholodyadya.config.JwtTokenUtil;
import org.vgk.kholodyadya.entity.Group;
import org.vgk.kholodyadya.entity.Product;
import org.vgk.kholodyadya.entity.User;
import org.vgk.kholodyadya.exceptions.*;
import org.vgk.kholodyadya.service.GroupService;
import org.vgk.kholodyadya.service.ImageService;
import org.vgk.kholodyadya.service.ProductService;
import org.vgk.kholodyadya.service.UserService;

import java.util.List;

@Service
@RestController
public class Controller {
    private final ProductService productService;
    private final UserService userService;
    private final GroupService groupService;
    private final ImageService imageService;
    private final AuthenticationManager authenticationManager;
    private final AuthUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;


    public Controller(ProductService productService, UserService userService, GroupService groupService, ImageService imageService, AuthenticationManager authenticationManager, AuthUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.productService = productService;
        this.userService = userService;
        this.groupService = groupService;
        this.imageService = imageService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/products")
    @Operation(summary = "method to add list of products to user's cart")
    public ResponseEntity<List<ProductWithImage>> addProducts(@RequestBody List<Product> cart) {
        int userId = userService.getAuthenticatedUserId();

        List<Product> addedProducts = productService.addProductList(
                cart,
                userId
        );

        return new ResponseEntity<>(addedProducts.stream()
                .map(p -> new ProductWithImage(p, imageService.loadImageOfProduct(p)))
                .toList(), HttpStatus.OK);
    }

    @PostMapping("/products/qr")
    @Operation(summary = "method to add list of products to user's cart within QR code")
    public ResponseEntity<List<ProductWithImage>> addProductsWithinQr(@RequestBody String qr) {
        try {
            int userId = userService.getAuthenticatedUserId();
            List<Product> addedProducts = productService.addProductsWithinQr(qr, userId);
            return new ResponseEntity<>(addedProducts.stream()
                    .map(p -> new ProductWithImage(p, imageService.loadImageOfProduct(p)))
                    .toList(), HttpStatus.OK);
        } catch (InvalidQrException | NonExistentUserIdException exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/products")
    @Operation(summary = "method to get list of user's products")
    public ResponseEntity<List<ProductWithImage>> getUserProducts() {
        int userId = userService.getAuthenticatedUserId();

        List<Product> products = productService.getUserProducts(userId);
        return new ResponseEntity<>(products.stream()
                .map(p -> new ProductWithImage(p, imageService.loadImageOfProduct(p)))
                .toList(), HttpStatus.OK);
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

    @PutMapping("/products/category")
    @Operation(summary = "method to change product category")
    public ResponseEntity<Product> changeProductCategory(@RequestBody int productId, @RequestBody String category) {
        try {
            Product replacedProduct = productService.changeProductCategory(productId, category);
            return new ResponseEntity<>(replacedProduct, HttpStatus.OK);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/group")
    @Operation(summary = "method to create a group")
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        int userId = userService.getAuthenticatedUserId();
        Group createdGroup = groupService.createGroup(group, userId);
        return new ResponseEntity<>(createdGroup, HttpStatus.OK);
    }

    @PostMapping("/group/{group_name}")
    @Operation(summary = "method to add a user in group")
    public ResponseEntity<String> addInGroup(@PathVariable("group_name") int groupId, @RequestBody String username) {
        int userId = userService.getAuthenticatedUserId();
        try {
            groupService.addInGroup(groupId, userId, username);
            return ResponseEntity.ok().build();
        } catch (NonExistentGroupException | NonExistentUserIdException | InsufficientPermissions exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/group")
    @Operation(summary = "method to get list of user's groups")
    public ResponseEntity<List<Group>> getListOfGroups() {
        int userId = userService.getAuthenticatedUserId();
        return new ResponseEntity<>(groupService.getUserGroups(userId), HttpStatus.OK);
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

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ProductWithImage {
        private Product product;
        private byte[] image;
    }
}