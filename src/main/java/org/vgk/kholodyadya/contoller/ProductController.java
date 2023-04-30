package org.vgk.kholodyadya.contoller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.vgk.kholodyadya.entity.Products;
import java.util.List;

@RestController
public class ProductController {

    @Getter
    @Setter
    private static class UserProducts {
        private String username;

        private List<String> products;
    }

    @GetMapping("/add_products")
    public String addProduct(@RequestBody UserProducts content) {
        System.out.println(content.username);
        for (var elem : content.products) {
            System.out.println(elem);
        }
        return "OKK";
    }

}