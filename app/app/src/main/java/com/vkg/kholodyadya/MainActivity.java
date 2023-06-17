package com.vkg.kholodyadya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.vkg.kholodyadya.adapters.CategoryAdapter;
import com.vkg.kholodyadya.adapters.ProductGVAAdapter;
import com.vkg.kholodyadya.models.Category;
import com.vkg.kholodyadya.models.Product;

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

    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category(1, "Фрукты"));
        categoryList.add(new Category(2, "Овощи"));
        categoryList.add(new Category(3, "Мясо"));
        categoryList.add(new Category(4, "Скоро испортится"));

        setCategoryRecycler(categoryList);

/*
        // TODO: remove hardcode
        String url = "http://51.250.21.160:8080/";
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .setHeader("X-Our-Header-1", "value1")
                    .setHeader("X-Our-Header-1", "value2")
                    .uri(new URI(url)).build();
        } catch (Exception e) {

        }
 */

        productList.add(new Product(0, "Бананы", "5 дней", R.drawable.ic_banana_hardcoded, 0));
        productList.add(new Product(1, "Огурцы", "5 дней", R.drawable.ic_banana_hardcoded, 0));
        productList.add(new Product(2, "Кабачки", "5 дней", R.drawable.ic_banana_hardcoded, 0));
        productList.add(new Product(3, "Баклажаны", "5 дней", R.drawable.ic_banana_hardcoded, 0));
        productList.add(new Product(4, "Макароны", "5 дней", R.drawable.ic_banana_hardcoded, 0));
        productList.add(new Product(5, "Пицца", "5 дней", R.drawable.ic_banana_hardcoded, 0));
        productList.add(new Product(6, "Бургер", "5 дней", R.drawable.ic_banana_hardcoded, 0));


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

    private void setProductGV(List<Product> productList) {
        productGV = findViewById(R.id.productGV);
        ProductGVAAdapter adapter = new ProductGVAAdapter(this, productList);
        productGV.setAdapter(adapter);
    }
}