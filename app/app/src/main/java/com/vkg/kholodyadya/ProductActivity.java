package com.vkg.kholodyadya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ImageView productImage = findViewById(R.id.productPageImage);
        TextView productName = findViewById(R.id.productPageTitle);
        TextView productCalories = findViewById(R.id.calories);
        TextView productProteins = findViewById(R.id.proteins);
        TextView productFats = findViewById(R.id.fats);
        TextView productCarbons = findViewById(R.id.carbons);

        productImage.setImageResource(getIntent().getIntExtra("productImage", 0));
        productName.setText(getIntent().getStringExtra("productTitle"));
        productCalories.setText(getIntent().getStringExtra("productCalories"));
        productProteins.setText(getIntent().getStringExtra("productCalories"));
        productFats.setText(getIntent().getStringExtra("productFats"));
        productCarbons.setText(getIntent().getStringExtra("productCarbons"));
    }

    public void deleteProduct(View view) {
        int itemId = getIntent().getIntExtra("productId", 0);
        String itemName = getIntent().getStringExtra("productTitle");
        String itemCategory = getIntent().getStringExtra("productCategory");
        APIWrapper wrapper = new APIWrapper(this.getString(R.string.test_token), this.getString(R.string.server_url));
        wrapper.deleteProduct(itemId, itemName, itemName);
//        MainActivity.removeById(itemId);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}