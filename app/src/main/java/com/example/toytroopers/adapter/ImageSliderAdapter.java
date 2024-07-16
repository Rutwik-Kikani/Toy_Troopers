package com.example.toytroopers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.toytroopers.R;
import com.example.toytroopers.databinding.ItemImageSliderBinding;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {
    private Context context;
    private List<String> imageUrls;

    public ImageSliderAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemImageSliderBinding binding = ItemImageSliderBinding.inflate(inflater, parent, false);
        return new ImageSliderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_menu_gallery)
                .into(holder.binding.imageViewSliderItem);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public static class ImageSliderViewHolder extends RecyclerView.ViewHolder {
        ItemImageSliderBinding binding;

        public ImageSliderViewHolder(@NonNull ItemImageSliderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
