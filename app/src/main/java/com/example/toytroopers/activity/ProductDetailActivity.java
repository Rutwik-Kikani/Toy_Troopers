package com.example.toytroopers.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.toytroopers.R;
import com.example.toytroopers.adapter.ImageSliderAdapter;
import com.example.toytroopers.databinding.ActivityMainBinding;
import com.example.toytroopers.databinding.ActivityProductDetailBinding;
import com.example.toytroopers.databinding.ActivityProductListBinding;
import com.example.toytroopers.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProductDetailBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference productRef;
    private DatabaseReference reviewsRef;
    private String productId;
    private ImageSliderAdapter imageSliderAdapter;
    //private ReviewAdapter reviewAdapter;
    private List<String> imageUrls;
    //private List<Review> reviews;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        database = FirebaseDatabase.getInstance();
        productId = getIntent().getStringExtra("productId");

        productRef = database.getReference("products").child(productId);
        reviewsRef = database.getReference("reviews");

        fetchProductDetails();
        //fetchProductReviews();
    }

    private void fetchProductDetails() {
        productRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Product product = snapshot.getValue(Product.class);
                if (product != null) {

                    binding.textViewProductName.setText(product.getName());
                    binding.textViewProductDescription.setText(product.getDescription());
                    binding.textViewProductPrice.setText("$ " + product.getPrice());

                    imageUrls = new ArrayList<>(product.getImages().values());
                    imageSliderAdapter = new ImageSliderAdapter(ProductDetailActivity.this, imageUrls);
                    binding.viewPagerProductImages.setAdapter(imageSliderAdapter);
                    binding.viewPagerProductImages.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

                    if (actionBar != null) {
                        actionBar.setTitle(product.getName());
                    }

                    //fetchReviews(product);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProductDetailsActivity", "Failed to fetch product details", error.toException());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void fetchReviews(Product product) {
        reviews = new ArrayList<>();
        for (String reviewId : product.getReviews().keySet()) {
            reviewsRef.child(reviewId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Review review = snapshot.getValue(Review.class);
                    if (review != null) {
                        reviews.add(review);
                        reviewAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("ProductDetailsActivity", "Failed to fetch reviews", error.toException());
                }
            });
        }

        reviewAdapter = new ReviewAdapter(ProductDetailsActivity.this, reviews);
        binding.recyclerViewReviews.setLayoutManager(new LinearLayoutManager(ProductDetailsActivity.this));
        binding.recyclerViewReviews.setAdapter(reviewAdapter);
    }*/
}