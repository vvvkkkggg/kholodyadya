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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vkg.kholodyadya.R;
import com.vkg.kholodyadya.models.Product;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    Context context;
    List<Product> productList;

    public CourseAdapter(Context context, List<Product> courses) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View courseItems = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new CourseAdapter.CourseViewHolder(courseItems);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        int imageId = productList.get(holder.getAdapterPosition()).getImgid();
        holder.productImage.setImageResource(imageId);
        holder.productName.setText(productList.get(holder.getAdapterPosition()).getTitle());
        holder.productDueDate.setText(productList.get(holder.getAdapterPosition()).getDueDate());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static final class CourseViewHolder extends RecyclerView.ViewHolder {

        CardView productCard;
        ImageView productImage;
        TextView productName, productDueDate;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            productCard = itemView.findViewById(R.id.productCard);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productDueDate = itemView.findViewById(R.id.productDueDate);
        }
    }

    ;
}
