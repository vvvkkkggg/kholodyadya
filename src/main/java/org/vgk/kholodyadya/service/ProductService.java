package org.vgk.kholodyadya.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;
import org.vgk.kholodyadya.entity.Product;
import org.vgk.kholodyadya.entity.ProductRelation;
import org.vgk.kholodyadya.exceptions.InvalidQrException;
import org.vgk.kholodyadya.exceptions.NonexistentUserIdException;
import org.vgk.kholodyadya.repository.ProductRelationRepository;
import org.vgk.kholodyadya.repository.ProductRepository;
import org.vgk.kholodyadya.repository.UserRepository;

import java.io.IOException;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ProductRelationRepository productRelationRepository;
    private final PaymentApi paymentApi;


    public ProductService(ProductRepository productRepository, UserRepository userRepository,
                          ProductRelationRepository productRelationRepository, PaymentApi paymentApi) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.productRelationRepository = productRelationRepository;
        this.paymentApi = paymentApi;
    }

    public void addProductsWithinQr(String qr, int userId) {
//        if (!userRepository.existsUserById(userId)) {
//            throw new NonexistentUserIdException("user does not exist");
//        }

        boolean isCorrectQr = ValidateQr.validateQr(qr);
        if (!isCorrectQr) {
            throw new InvalidQrException("qr code has invalid format");
        }

        JsonObject result;
        try {
            result = paymentApi.getReceipt(qr);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        JsonArray products = result.getAsJsonObject("ticket")
                .getAsJsonObject("document")
                .getAsJsonObject("receipt")
                .getAsJsonArray("items");

        products.asList().forEach(product -> addProduct(product.getAsJsonObject().get("name").toString(), userId));
    }

    public void addProduct(String productName, int userId) {
        Product result = productRepository.save(Product.builder().productName(productName).build());
        productRelationRepository.save(ProductRelation.builder().
                relationId(new ProductRelation.ProductRelationId(result.getProductId(), userId)).build());
    }
}