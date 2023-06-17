package com.vkg.kholodyadya.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vkg.kholodyadya.R;
import com.vkg.kholodyadya.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductGVAAdapter extends ArrayAdapter<Product> {

    public ProductGVAAdapter(@NonNull Context context, List<Product> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
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
        return listitemView;
    }
}
