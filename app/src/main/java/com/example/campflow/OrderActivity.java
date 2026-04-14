package com.example.campflow;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        MaterialToolbar toolbar = findViewById(R.id.orderToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        RecyclerView recyclerView = findViewById(R.id.orderRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Order> orderList = new ArrayList<>();
        // Dummy data
        orderList.add(new Order("12345", "Spicy Noodles", "150", R.drawable.noodle, "Preparing", "24 May 2024"));
        orderList.add(new Order("12346", "Pav Bhaji", "120", R.drawable.pav, "Delivered", "23 May 2024"));
        orderList.add(new Order("12347", "Paneer Pizza", "200", R.drawable.paneer_pizza, "Placed", "24 May 2024"));

        OrderAdapter adapter = new OrderAdapter(orderList, order -> {
            Intent intent = new Intent(OrderActivity.this, OrderDetailActivity.class);
            intent.putExtra("order", order);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
    }
}