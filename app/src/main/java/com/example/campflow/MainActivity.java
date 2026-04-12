package com.example.campflow;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.widget.Toast;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ViewPager2 bannerSlider;
    BannerAdapter bannerAdapter;
    List<Integer> bannerList;

    RecyclerView categoryRecycler;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Handle Profile Picture Click
        ShapeableImageView profileImg = findViewById(R.id.profileImg);
        profileImg.setOnClickListener(v -> {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                // User is logged in, go to Profile
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            } else {
                // User is not logged in, go to Login/Signup
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        bannerSlider = findViewById(R.id.bannerSlider);

        bannerList = new ArrayList<>();
        bannerList.add(R.drawable.banner);
        bannerList.add(R.drawable.banner1);
        bannerList.add(R.drawable.banner2);

        bannerAdapter = new BannerAdapter(bannerList, this);
        bannerSlider.setAdapter(bannerAdapter);

        Handler sliderHandler = new Handler(Looper.getMainLooper());
        Runnable sliderRunnable = new Runnable() {
            @Override
            public void run() {
                int nextItem = bannerSlider.getCurrentItem() + 1;
                if (nextItem >= bannerList.size()) {
                    nextItem = 0;
                }
                bannerSlider.setCurrentItem(nextItem, true);
                sliderHandler.postDelayed(this, 3000); // 3 seconds
            }
        };

        categoryRecycler = findViewById(R.id.categoryRecycler);
        categoryList = new ArrayList<>();
        categoryList.add(new Category("Drinks", R.drawable.drink));
        categoryList.add(new Category("Dessert", R.drawable.desseret));
        categoryList.add(new Category("Fast Food", R.drawable.sandwich));
        categoryList.add(new Category("Snacks", R.drawable.pav));
        categoryList.add(new Category("South Indian", R.drawable.dosa));
        categoryList.add(new Category("Chinese", R.drawable.noodle));

        categoryAdapter = new CategoryAdapter(this, categoryList);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        categoryRecycler.setLayoutManager(gridLayoutManager);
        categoryRecycler.setAdapter(categoryAdapter);

        RecyclerView foodRecycler = findViewById(R.id.foodRecycler);

        List<FoodItem> foodList = new ArrayList<>();
        foodList.add(new FoodItem("Spicy Noodles", "1500", R.drawable.noodle));
        foodList.add(new FoodItem("Pav Bhaji", "1200", R.drawable.pav));
        foodList.add(new FoodItem("Sandwich", "1000", R.drawable.sandwich));
        foodList.add(new FoodItem("Dosa", "900", R.drawable.dosa));

        foodRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        foodRecycler.setAdapter(new FoodAdapter(foodList));

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                return true;
            } else if (item.getItemId() == R.id.favourite) {
                startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
                return true;
            } else if (item.getItemId() == R.id.cart) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                return true;
            } else if (item.getItemId() == R.id.profile) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                return true;
            }
            return false;
        });

        sliderHandler.postDelayed(sliderRunnable, 3000);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}