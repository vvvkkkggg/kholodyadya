package org.vgk.kholodyadya.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.vgk.kholodyadya.contoller.Controller;
import org.vgk.kholodyadya.entity.Product;
import org.vgk.kholodyadya.entity.ProductRelation;
import org.vgk.kholodyadya.exceptions.InvalidQrException;
import org.vgk.kholodyadya.exceptions.NonExistentProduct;
import org.vgk.kholodyadya.exceptions.NonExistentUserIdException;
import org.vgk.kholodyadya.repository.ProductRelationRepository;
import org.vgk.kholodyadya.repository.ProductRepository;
import org.vgk.kholodyadya.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<Product> addProductsWithinQr(String qr, int userId) {
        List<Product> addedProducts = new ArrayList<>();
        if (!userRepository.existsUserById(userId)) {
            throw new NonExistentUserIdException("user does not exist");
        }

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

        products.asList().forEach(product -> addedProducts.add(
                addProduct(buildProductFromJson(product.getAsJsonObject()), userId))
        );
        return addedProducts;
    }

    public Product addProduct(Product product, int userId) {
        Product result = productRepository.save(product);
        productRelationRepository.save(ProductRelation.builder().
                relationId(new ProductRelation.ProductRelationId(result.getProductId(), userId)).build());
        return product;
    }

    public List<Product> getUserProducts(int userId) {
        List<ProductRelation> products = productRelationRepository.findAllByRelationIdUserId(userId);
        return products.stream()
                .map(relation -> productRepository.findByProductId(relation.getRelationId().getProductId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public Product changeProductCategory(int productId, String category) {
        Product product = productRepository.getReferenceById(productId);
        product.setCategory(category);
        productRepository.save(product);
        return product;
    }

    public List<Product> addProductList(List<Product> productList, int userId) {
        List<Product> addedProducts = new ArrayList<>();
        for (Product product : productList) {
            addedProducts.add(addProduct(product, userId));
        }
        return addedProducts;
    }

    public void deleteProduct(Product product, int userId) {
        Optional<Product> foundProduct = productRepository.findByProductName(product.getProductName());
        if (foundProduct.isEmpty()) {
            throw new NonExistentProduct("Non existent product name:" + product.getProductName());
        }
        ProductRelation relation = ProductRelation.builder()
                .relationId(new ProductRelation.ProductRelationId(
                        foundProduct.get().getProductId(), userId
                ))
                .build();
        if (productRelationRepository.findProductRelationByRelationId(relation.getRelationId()).isEmpty()) {
            throw new NonExistentProduct("Non existent product name:" + product.getProductName());
        }
        productRelationRepository.deleteByRelationId(relation.getRelationId());
    }

    private Product buildProductFromJson(JsonObject productAsJson) {
        String name = String.valueOf(productAsJson.get("name")).split(" ")[0].replace("\"", "");
        String category = name;
        return Product.builder()
                .category(category)
                .productName(name)
                .build();
    }
}