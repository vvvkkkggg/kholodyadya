package org.vgk.kholodyadya.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.vgk.kholodyadya.entity.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    @Value("${imagesFolder}")
    private String imagesFolder;

    @Value("${defaultImage}")
    private String defaultImage;

    public byte[] loadImageOfProduct(Product product) {
        Path pathToImage = Paths.get(imagesFolder, product.getCategory().replace("\"", "") + ".png");
        try {
            return Files.readAllBytes(pathToImage);
        } catch (IOException e) {
            try {
                return Files.readAllBytes(Paths.get(imagesFolder, defaultImage));
            } catch (IOException ex) {
                return null;
            }
        }
    }
}
