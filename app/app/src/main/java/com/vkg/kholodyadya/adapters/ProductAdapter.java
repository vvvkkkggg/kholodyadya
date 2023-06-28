package com.vkg.kholodyadya.adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vkg.kholodyadya.ProductActivity;
import com.vkg.kholodyadya.ProductRepository;
import com.vkg.kholodyadya.R;
import com.vkg.kholodyadya.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    List<Product> productList;
    ProductRepository productRepo;

    public ProductAdapter(Context context, List<Product> productList, ProductRepository productRepo) {
        this.context = context;
        this.productList = productList;
        this.productRepo = productRepo;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View productItems = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(productItems);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        int imageId = productList.get(holder.getAdapterPosition()).getImgid();
        holder.productImage.setImageResource(imageId);
        holder.productName.setText(productList.get(holder.getAdapterPosition()).getTitle());
        holder.productDueDate.setText(productList.get(holder.getAdapterPosition()).getDueDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(
                        (Activity) context,
                        new Pair<View, String>(holder.productImage, "image"));


                intent.putExtra("image", imageId);
                intent.putExtra("title", productList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("calories", productList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("proteins", productList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("fats", productList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra("carbs", productList.get(holder.getAdapterPosition()).getTitle());

                context.startActivity(intent, options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static final class ProductViewHolder extends RecyclerView.ViewHolder {

        CardView productCard;
        ImageView productImage;
        TextView productName, productDueDate;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productCard = itemView.findViewById(R.id.productCard);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            productDueDate = itemView.findViewById(R.id.productDueDate);
        }
    }

    ;


}
