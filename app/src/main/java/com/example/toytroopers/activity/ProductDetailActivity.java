package com.example.toytroopers.activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.toytroopers.R;
import com.example.toytroopers.databinding.ActivityMainBinding;
import com.example.toytroopers.databinding.ActivityProductDetailBinding;
import com.example.toytroopers.databinding.ActivityProductListBinding;

public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProductDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

    }
}