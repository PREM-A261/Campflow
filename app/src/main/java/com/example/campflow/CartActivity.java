package com.example.campflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.widget.TextView;
import android.app.Dialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView cartRecycler;
    CartAdapter cartAdapter;
    Button buyNowBtn, btnBack;
    TextView emptyCartText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecycler = findViewById(R.id.cartRecycler);
        buyNowBtn = findViewById(R.id.btnBuyNowCart);
        btnBack = findViewById(R.id.btnBackCart);
        emptyCartText = findViewById(R.id.emptyCartText);

        cartRecycler.setLayoutManager(new LinearLayoutManager(this));

        List<FoodItem> cartItems = CartManager.getInstance().getCartItems();
        cartAdapter = new CartAdapter(cartItems);

        cartRecycler.setAdapter(cartAdapter);

        updateUIState(cartItems);

        btnBack.setOnClickListener(v -> finish());

        buyNowBtn.setOnClickListener(v -> {

            if (CartManager.getInstance().getCartItems().isEmpty()) {
                Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                return;
            }

            // Go to Payment Activity
            startActivity(new Intent(this, PaymentActivity.class));
        });
    }

    private void updateUIState(List<FoodItem> cartItems) {
        if (cartItems.isEmpty()) {
            emptyCartText.setVisibility(View.VISIBLE);
            cartRecycler.setVisibility(View.GONE);
            buyNowBtn.setVisibility(View.GONE);
        } else {
            emptyCartText.setVisibility(View.GONE);
            cartRecycler.setVisibility(View.VISIBLE);
            buyNowBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUIState(CartManager.getInstance().getCartItems());
        cartAdapter.notifyDataSetChanged();
    }
}
