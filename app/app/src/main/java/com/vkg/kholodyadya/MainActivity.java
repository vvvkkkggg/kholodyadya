package com.vkg.kholodyadya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.vkg.kholodyadya.adapters.CategoryAdapter;
import com.vkg.kholodyadya.adapters.ProductGVAAdapter;
import com.vkg.kholodyadya.models.Category;
import com.vkg.kholodyadya.models.Product;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/*
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
*/


public class MainActivity extends AppCompatActivity {

    RecyclerView categoryRecycler;

    GridView productGV;

    static List<Product> productList = new ArrayList<>();
    static List<Product> fullProductList = new ArrayList<>();
    static ProductRepository repo = new ProductRepository();
    static ProductGVAAdapter productGVAAdapter;

    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Всё"));
        categoryList.add(new Category(2, "Фрукты"));
        categoryList.add(new Category(3, "Овощи"));
        categoryList.add(new Category(4, "Мясо"));
        categoryList.add(new Category(5, "Скоро испортится"));

        setCategoryRecycler(categoryList);



        APIWrapper wrapper = new APIWrapper(this.getString(R.string.test_token), this.getString(R.string.server_url));
        fullProductList.clear();
        fullProductList.addAll(wrapper.getProducts());

        for (int i = 0; i < fullProductList.size(); i++) {
            Product p = fullProductList.get(i);
            p.setLocalId(i);
            p.setImgid(repo.getProductImageId(p));
            p.setCategory(repo.getProductCategory(p));
        }
//        for (int i = 0; i < productList.size(); i++) {
//            productList.get(i).setLocalId(i);
//        }
//        ArrayList <Product> kekList = new ArrayList<>();
//        productList.add(new Product(0, "Бананы", "5 дней", R.drawable.ic_banana_hardcoded, ""));
//        productList.add(new Product(1, "Огурцы", "5 дней", R.drawable.ic_banana_hardcoded, ""));
//        productList.add(new Product(2, "Кабачки", "5 дней", R.drawable.ic_banana_hardcoded, ""));
//        productList.add(new Product(3, "Баклажаны", "5 дней", R.drawable.ic_banana_hardcoded, ""));
//        productList.add(new Product(4, "Макароны", "5 дней", R.drawable.ic_banana_hardcoded, ""));
//        productList.add(new Product(5, "Пицца", "5 дней", R.drawable.ic_banana_hardcoded, ""));
//        productList.add(new Product(6, "Бургер", "5 дней", R.drawable.ic_banana_hardcoded, ""));
        productList.clear();
        productList.addAll(fullProductList);

        setProductGV(productList);
    }



    private void setCategoryRecycler(List<Category> categoryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        categoryRecycler = findViewById(R.id.categoryRecycler);
        categoryRecycler.setLayoutManager(layoutManager);

        categoryAdapter = new CategoryAdapter(this, categoryList);
        categoryRecycler.setAdapter(categoryAdapter);

    }

    public void openQRPage(View view) {
        Intent intent = new Intent(this, QRActivity.class);
        startActivity(intent);
    }

    public static void removeById(int productId) {
        for (int i = 0; i < fullProductList.size(); i++) {
            if (productList.get(productId).getProductId() == productId) {
                fullProductList.remove(i);
                break;
            }
        }
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(productId).getProductId() == productId) {
                productList.remove(i);
                break;
            }
        }
        productGVAAdapter.notifyDataSetChanged();
    }

    private void setProductGV(List<Product> productList) {
        productGV = findViewById(R.id.productGV);
        productGVAAdapter = new ProductGVAAdapter(this, productList, repo);
        productGV.setAdapter(productGVAAdapter);
    }

    public static void showProductsByCategory(String category) {
        if (category == "Всё") {
            productList.clear();
            productList.addAll(fullProductList);
            productGVAAdapter.notifyDataSetChanged();
            return;
        }

        List<Product> filterProducts = new ArrayList<>();
        for (Product c : fullProductList) {
            if (c.getCategory() == category) {
                filterProducts.add(c);
            }
        }

        productList.clear();
        productList.addAll(filterProducts);

        productGVAAdapter.notifyDataSetChanged();
    }
}