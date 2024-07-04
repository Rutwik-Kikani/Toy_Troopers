package com.example.toytroopers.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.toytroopers.R;
import com.example.toytroopers.activity.ProductListActivity;
import com.example.toytroopers.model.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> {

    private List<Category> categoriesList;
    private Context context;

    public CategoriesAdapter(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categoriesList.get(position);
        holder.textViewCategoryName.setText(category.getCategoryName());

        int numberOfColumns = 3;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int itemWidth = screenWidth / numberOfColumns;

        // Set the width and height of the ImageView to maintain aspect ratio
        ViewGroup.LayoutParams layoutParams = holder.imageViewCategory.getLayoutParams();
        layoutParams.width = itemWidth;
        layoutParams.height = itemWidth; // Square aspect ratio
        holder.imageViewCategory.setLayoutParams(layoutParams);

        Glide.with(context)
                .load(category.getCategoryImageURL())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.baseline_category_24)
                        .circleCrop())
                .into(holder.imageViewCategory);

        // Handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start new activity here, passing necessary data if needed
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("categoryId", category.getCategoryId()); // Pass category ID or other data
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCategory;
        TextView textViewCategoryName;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            imageViewCategory = itemView.findViewById(R.id.imageViewCategory);
            textViewCategoryName = itemView.findViewById(R.id.textViewCategoryName);
        }
    }
}
