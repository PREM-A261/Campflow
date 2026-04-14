package com.example.campflow;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;
import com.google.firebase.auth.FirebaseAuth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Switch;
import android.widget.Toast;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
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
    
    FoodAdapter foodAdapter;
    SearchBar searchBar;
    SearchView searchView;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else if (id == R.id.nav_orders) {
                startActivity(new Intent(MainActivity.this, OrderActivity.class));
            } else if (id == R.id.nav_payment) {
                startActivity(new Intent(MainActivity.this, PaymentActivity.class));
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            } else if (id == R.id.nav_logout) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Dark Mode Toggle
        Switch darkModeSwitch = (Switch) navigationView.getMenu().findItem(R.id.nav_dark_mode).getActionView();
        SharedPreferences sharedPreferences = getSharedPreferences("ThemePrefs", MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean("isDarkMode", false);
        darkModeSwitch.setChecked(isDarkMode);
        
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            sharedPreferences.edit().putBoolean("isDarkMode", isChecked).apply();
        });

        // Handle Profile Picture Click
        ShapeableImageView profileImg = findViewById(R.id.profileImg);
        profileImg.setOnClickListener(v -> {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            } else {
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
                sliderHandler.postDelayed(this, 3000);
            }
        };
        sliderHandler.postDelayed(sliderRunnable, 3000);

        categoryRecycler = findViewById(R.id.categoryRecycler);
        categoryList = new ArrayList<>();
        categoryList.add(new Category("Drinks", R.drawable.drink));
        categoryList.add(new Category("Dessert", R.drawable.desseret));
        categoryList.add(new Category("Fast Food", R.drawable.sandwich));
        categoryList.add(new Category("Snacks", R.drawable.pav));
        categoryList.add(new Category("South Indian", R.drawable.dosa));
        categoryList.add(new Category("Chinese", R.drawable.noodle));

        categoryAdapter = new CategoryAdapter(this, categoryList);
        categoryRecycler.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false));
        categoryRecycler.setAdapter(categoryAdapter);

        RecyclerView foodRecycler = findViewById(R.id.foodRecycler);
        List<FoodItem> foodList = new ArrayList<>();
        foodList.add(new FoodItem("Spicy Noodles", "150", R.drawable.noodle));
        foodList.add(new FoodItem("Pav Bhaji", "120", R.drawable.pav));
        foodList.add(new FoodItem("Sandwich", "100", R.drawable.sandwich));
        foodList.add(new FoodItem("Dosa", "90", R.drawable.dosa));
        
        foodList.add(new FoodItem("Paneer Pizza", "200", R.drawable.paneer_pizza));
        foodList.add(new FoodItem("Burger", "110", R.drawable.burger));
        foodList.add(new FoodItem("Chocolate Cake", "180", R.drawable.desseret));
        foodList.add(new FoodItem("Fresh Juice", "50", R.drawable.drink));

        foodAdapter = new FoodAdapter(foodList);
        foodRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        foodRecycler.setAdapter(foodAdapter);

        searchBar = findViewById(R.id.searchBar);
        searchView = findViewById(R.id.searchView);
        
        searchView.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            searchBar.setText(searchView.getText());
            searchView.hide();
            foodAdapter.filter(searchView.getText().toString());
            return false;
        });
        
        searchView.getEditText().addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                foodAdapter.filter(s.toString());
            }
            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawer_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}