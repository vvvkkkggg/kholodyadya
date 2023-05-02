package org.vgk.kholodyadya.contoller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.vgk.kholodyadya.entity.User;
import org.vgk.kholodyadya.exceptions.InvalidQrException;
import org.vgk.kholodyadya.exceptions.NonexistentUserIdException;
import org.vgk.kholodyadya.repository.ProductRepository;
import org.vgk.kholodyadya.repository.UserRepository;
import org.vgk.kholodyadya.service.ValidateQr;

import java.util.List;

@Service
@RestController
public class Controller {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Controller(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
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
    public void addProducts(@PathVariable("user_id") Long user_id, @RequestBody UserProductsList cart) {
        User user = userRepository.findById(user_id)
            .orElseThrow(() -> new NonexistentUserIdException("non existent user id passed"));
    }

    @PostMapping("/products/qr/{user_id}")
    public void addProductsWithinQr(@PathVariable("user_id") Long user_id, @RequestBody String qr) {
        boolean isCorrect = ValidateQr.validateQr(qr);
        if (!isCorrect) {
            throw new InvalidQrException("qr format is invalid");
        }

    }

}