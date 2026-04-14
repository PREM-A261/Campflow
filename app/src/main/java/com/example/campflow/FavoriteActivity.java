package com.example.campflow;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FavoriteAdapter favoriteAdapter;
    TextView emptyFavoriteText;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.favoriteRecyclerView);
        emptyFavoriteText = findViewById(R.id.emptyFavoriteText);
        btnBack = findViewById(R.id.btnBackFavorite);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<FoodItem> favorites = FavoriteManager.getFavorites();
        favoriteAdapter = new FavoriteAdapter(favorites);
        recyclerView.setAdapter(favoriteAdapter);

        updateEmptyState(favorites);

        btnBack.setOnClickListener(v -> finish());
    }

    private void updateEmptyState(List<FoodItem> favorites) {
        if (favorites.isEmpty()) {
            emptyFavoriteText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptyFavoriteText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateEmptyState(FavoriteManager.getFavorites());
        favoriteAdapter.notifyDataSetChanged();
    }
}
