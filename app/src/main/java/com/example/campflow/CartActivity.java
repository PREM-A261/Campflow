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

public class CartActivity extends AppCompatActivity {

    RecyclerView cartRecycler;
    CartAdapter cartAdapter;
    Button buyNowBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartRecycler = findViewById(R.id.cartRecycler);
        buyNowBtn = findViewById(R.id.btnBuyNowCart);

        cartRecycler.setLayoutManager(new LinearLayoutManager(this));

        cartAdapter = new CartAdapter(
                CartManager.getInstance().getCartItems()
        );

        cartRecycler.setAdapter(cartAdapter);

        updateBuyButtonVisibility();

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
            
            /* 
            // Previous logic for dialog confirmation - keeping it commented if needed later
            // Calculate total price
            int total = 0;
            for (FoodItem item : CartManager.getInstance().getCartItems()) {
                total += Integer.parseInt(item.getPrice());
            }

            showOrderConfirmationDialog(total);

            // Clear cart after order
            CartManager.getInstance().getCartItems().clear();
            cartAdapter.notifyDataSetChanged();
            updateBuyButtonVisibility();
            */
        });
    }

    private void showOrderConfirmationDialog(int totalAmount) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_order_confirmation);

        TextView orderItems = dialog.findViewById(R.id.txtOrderItems);
        TextView price = dialog.findViewById(R.id.txtPrice);
        TextView pickup = dialog.findViewById(R.id.txtPickup);
        TextView uniqueId = dialog.findViewById(R.id.txtUniqueId);
        Button closeBtn = dialog.findViewById(R.id.btnClose);

        StringBuilder itemsText = new StringBuilder();
        int total = 0;

        for (FoodItem item : CartManager.getInstance().getCartItems()) {

            int itemTotal = Integer.parseInt(item.getPrice()) * item.getQuantity();
            total += itemTotal;

            itemsText.append("• ")
                    .append(item.getName())
                    .append("  x")
                    .append(item.getQuantity())
                    .append("  - ₹")
                    .append(itemTotal)
                    .append("\n");
        }

        orderItems.setText(itemsText.toString());
        price.setText("Total: ₹ " + total);

        // Pickup Time (Current time + 30 mins)
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.add(java.util.Calendar.MINUTE, 30);

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("hh:mm a");
        pickup.setText("Pickup Time: " + sdf.format(calendar.getTime()));

        // Unique Order ID
        String orderId = "CF" + System.currentTimeMillis();
        uniqueId.setText("Verification ID: " + orderId);

        closeBtn.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }


    private void updateBuyButtonVisibility() {

        if (CartManager.getInstance().getCartItems().isEmpty()) {
            buyNowBtn.setVisibility(View.GONE);
        } else {
            buyNowBtn.setVisibility(View.VISIBLE);
        }
    }
}