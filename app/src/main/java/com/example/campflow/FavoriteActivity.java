package com.example.campflow;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FavoriteActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FavoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        recyclerView = findViewById(R.id.favoriteRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoriteAdapter = new FavoriteAdapter(FavoriteManager.getFavorites());
        recyclerView.setAdapter(favoriteAdapter);
    }
}