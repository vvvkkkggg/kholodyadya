package com.vkg.kholodyadya;

import com.vkg.kholodyadya.models.Product;
import com.vkg.kholodyadya.models.ProductInfo;

import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
    Map<String, ProductInfo> repo = new HashMap<>();

    public ProductRepository() {
        repo.put("ГРЕЙПФРУТ", new ProductInfo(
                R.drawable.ic_product_grapefruit,
                "Фрукты",
                4,
                0,
                0,
                0
        ));
        repo.put("РИГЛИ", new ProductInfo(
                R.drawable.ic_product_cervelat,
                "Мясо",
                4,
                0,
                0,
                0
        ));
        repo.put("ПАПА", new ProductInfo(
                R.drawable.ic_product_sausage,
                "Мясо",
                4,
                0,
                0,
                0
        ));
        repo.put("GUSTO", new ProductInfo(
                R.drawable.ic_product_spaghetti,
                "bacalea",
                4,
                0,
                0,
                0
        ));
        repo.put("BORJOMI", new ProductInfo(
                R.drawable.ic_product_borjomi,
                "beverage",
                4,
                0,
                0,
                0
        ));
        repo.put("SИБИРСКАЯ", new ProductInfo(
                R.drawable.ic_product_dumplings_jpg,
                "Мясо",
                4,
                0,
                0,
                0
        ));
        repo.put("АБРИКОСЫ", new ProductInfo(
                R.drawable.ic_product_apricot,
                "Фрукты",
                4,
                0,
                0,
                0
        ));
        repo.put("ЯБЛОКИ", new ProductInfo(
                R.drawable.ic_product_apple,
                "Фрукты",
                4,
                0,
                0,
                0
        ));
        repo.put("ВИНОГРАД", new ProductInfo(
                R.drawable.ic_product_grape,
                "Фрукты",
                4,
                0,
                0,
                0
        ));
        repo.put("НЕКТАРИНЫ", new ProductInfo(
                R.drawable.ic_product_nectarines,
                "Фрукты",
                4,
                0,
                0,
                0
        ));
    }

    public int getProductImageId(Product product) {
        return repo.get(product.getTitle()).getImgid();
    }

    public String getProductCategory(Product product) {
        return repo.get(product.getTitle()).getCategory();
    }

    public int getProductCalories(Product product) {
        return repo.get(product.getTitle()).getCalories();
    }
    public int getProductProteins(Product product) {
        return repo.get(product.getTitle()).getProtein();
    }
    public int getProductFats(Product product) {
        return repo.get(product.getTitle()).getFat();
    }
    public int getProductCarbons(Product product) {
        return repo.get(product.getTitle()).getCarbon();
    }
}
