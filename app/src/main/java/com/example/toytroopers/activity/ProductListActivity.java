package com.example.toytroopers.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.toytroopers.adapter.ProductsAdapter;
import com.example.toytroopers.databinding.ActivityProductListBinding;
import com.example.toytroopers.model.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    ActivityProductListBinding binding;
    private ProductsAdapter productsAdapter;
    private List<Product> productList;
    private DatabaseReference productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getIntent().getStringExtra("category"));
        }

        binding.recyclerViewProducts.setHasFixedSize(true);
        binding.recyclerViewProducts.setLayoutManager(new GridLayoutManager(this, 2));

        productList = new ArrayList<>();
        productsAdapter = new ProductsAdapter(ProductListActivity.this,productList);
        binding.recyclerViewProducts.setAdapter(productsAdapter);


        String categoryId = getIntent().getStringExtra("categoryId");

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        productsRef = firebaseDatabase.getReference().child("products");

        // Query products under the selected category
        Query query = productsRef.orderByChild("categoryId").equalTo(categoryId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    String productId = productSnapshot.getKey();
                    String name = (String) productSnapshot.child("name").getValue();
                    String description = (String) productSnapshot.child("description").getValue();
                    String imageUrl = (String) productSnapshot.child("imageUrl").getValue();
                    double price = 0.0;
                    if (productSnapshot.child("price").getValue() instanceof Long) {
                        price = ((Long) productSnapshot.child("price").getValue()).doubleValue();
                    } else {
                        price = (double) productSnapshot.child("price").getValue();
                    }
                    int stockQuantity = ((Long) productSnapshot.child("stockQuantity").getValue()).intValue();

                    Product product = new Product(productId, categoryId, name, description, imageUrl, price, stockQuantity);
                    productList.add(product);
                }
                productsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
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
}