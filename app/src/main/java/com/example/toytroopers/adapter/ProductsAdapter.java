package com.example.toytroopers.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.toytroopers.R;
import com.example.toytroopers.activity.ProductDetailActivity;
import com.example.toytroopers.activity.ProductListActivity;
import com.example.toytroopers.databinding.ItemProductBinding;
import com.example.toytroopers.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;

    public ProductsAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemProductBinding binding = ItemProductBinding.inflate(inflater, parent, false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.binding.textViewProductName.setText(product.getName());
        holder.binding.textViewProductPrice.setText("$ " + product.getPrice());

        int numberOfColumns = 2;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int itemWidth = screenWidth / numberOfColumns;

        // Set the width and height of the ImageView to maintain aspect ratio
        ViewGroup.LayoutParams layoutParams = holder.binding.imageViewProduct.getLayoutParams();
        layoutParams.width = itemWidth;
        layoutParams.height = itemWidth; // Square aspect ratio
        holder.binding.imageViewProduct.setLayoutParams(layoutParams);

        // Load image using Glide
        Glide.with(context)
                .load(product.getImageUrl())
                .apply(new RequestOptions()
                        .centerCrop())
                .into(holder.binding.imageViewProduct);
//        .placeholder(R.drawable.placeholder_image)

        holder.binding.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("productId", product.getProductId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ItemProductBinding binding;

        public ProductViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
