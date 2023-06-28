package com.vkg.kholodyadya.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vkg.kholodyadya.ProductActivity;
import com.vkg.kholodyadya.ProductRepository;
import com.vkg.kholodyadya.R;
import com.vkg.kholodyadya.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductGVAAdapter extends ArrayAdapter<Product> {

    Context context;
    ProductRepository repo;

    public ProductGVAAdapter(@NonNull Context context, List<Product> productModelArrayList, ProductRepository repo) {
        super(context, 0, productModelArrayList);
        this.context = context;
        this.repo = repo;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.product_item, parent, false);
        }

        Product product = getItem(position);
        TextView productTV = listitemView.findViewById(R.id.productName);
        ImageView productIV = listitemView.findViewById(R.id.productImage);

        productTV.setText((product.getTitle()));
        productIV.setImageResource(product.getImgid());

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        (Activity) context,
                        new Pair<View, String>(productIV, "productImage"));


                intent.putExtra("productImage", product.getImgid());
                intent.putExtra("productTitle", product.getTitle());
                intent.putExtra("productCalories", String.valueOf(repo.getProductCalories(product)));
                intent.putExtra("productProteins", String.valueOf(repo.getProductProteins(product)));
                intent.putExtra("productFats", String.valueOf(repo.getProductFats(product)));
                intent.putExtra("productCarbons", String.valueOf(repo.getProductCarbons(product)));
                intent.putExtra("productId", product.getProductId());
                intent.putExtra("productCategory", product.getCategory());

                context.startActivity(intent, options.toBundle());
            }
        });
        return listitemView;
    }




}
