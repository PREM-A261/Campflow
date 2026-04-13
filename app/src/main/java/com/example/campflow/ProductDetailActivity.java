package com.example.campflow;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView image;
    TextView name, price, description;
    Button addToCart, buyNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        // ✅ Initialize Views AFTER setContentView
        image = findViewById(R.id.detailImage);
        name = findViewById(R.id.detailName);
        price = findViewById(R.id.detailPrice);
        description = findViewById(R.id.detailDescription);
        addToCart = findViewById(R.id.btnAddToCart);
        buyNow = findViewById(R.id.btnBuyNow);

        // ✅ Get Data From Intent
        Intent intent = getIntent();

        String productName = intent.getStringExtra("name");
        String productPrice = intent.getStringExtra("price");
        int productImage = intent.getIntExtra("image", 0);
        String productDescription = intent.getStringExtra("description");

        // ✅ Set Data to Views
        name.setText(productName);
        price.setText("₹" + productPrice);
        image.setImageResource(productImage);
        description.setText(productDescription);

        // ✅ Button Click Listeners
        addToCart.setOnClickListener(v -> {

            FoodItem item = new FoodItem(
                    productName,
                    productPrice,
                    productImage
            );

            CartManager.getInstance().addToCart(item);

            Toast.makeText(this, "Added to cart!", Toast.LENGTH_SHORT).show();
        });

        buyNow.setOnClickListener(v -> {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
            } else {
                startActivity(new Intent(this, PaymentActivity.class));
            }
        });
    }
}