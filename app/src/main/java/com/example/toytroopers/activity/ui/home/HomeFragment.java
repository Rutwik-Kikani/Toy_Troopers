package com.example.toytroopers.activity.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toytroopers.adapter.CategoriesAdapter;
import com.example.toytroopers.databinding.FragmentHomeBinding;
import com.example.toytroopers.model.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private List<Category> categoriesList;
    private CategoriesAdapter categoriesAdapter;
    private DatabaseReference categoriesRef;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*//HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;*/

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();





        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewCategories = binding.recyclerViewCategories;
        recyclerViewCategories.setHasFixedSize(true);
        recyclerViewCategories.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        categoriesList = new ArrayList<>();
        categoriesAdapter = new CategoriesAdapter(categoriesList);
        recyclerViewCategories.setAdapter(categoriesAdapter);

        // Initialize Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        categoriesRef = firebaseDatabase.getReference().child("categories");

        // Fetch categories from Firebase
        categoriesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoriesList.clear();
                for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()) {
                    String categoryId = categorySnapshot.getKey(); // Fetch categoryId
                    String categoryName = (String) categorySnapshot.child("categoryName").getValue();
                    String categoryImageURL = (String) categorySnapshot.child("categoryImageURL").getValue();

                    Category category = new Category(categoryId, categoryImageURL, categoryName);
                    categoriesList.add(category);
                }
                categoriesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}