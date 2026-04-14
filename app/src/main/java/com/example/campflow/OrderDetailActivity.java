package com.example.campflow;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class OrderDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Order order = (Order) getIntent().getSerializableExtra("order");

        MaterialToolbar toolbar = findViewById(R.id.detailToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        if (order != null) {
            ((ImageView) findViewById(R.id.detailImage)).setImageResource(order.getImageResource());
            ((TextView) findViewById(R.id.detailFoodName)).setText(order.getFoodName());
            ((TextView) findViewById(R.id.detailOrderId)).setText("Order #" + order.getOrderId());
            ((TextView) findViewById(R.id.detailPrice)).setText("₹" + order.getPrice());

            updateTrackingSteps(order.getStatus());
        }
    }

    private void updateTrackingSteps(String status) {
        setupStep(findViewById(R.id.stepPlaced), "Order Placed", "Your order has been received", true, true);
        
        boolean isPreparing = status.equals("Preparing") || status.equals("Out for Delivery") || status.equals("Delivered");
        setupStep(findViewById(R.id.stepPreparing), "Preparing", "Chef is cooking your food", isPreparing, true);
        
        boolean isOut = status.equals("Out for Delivery") || status.equals("Delivered");
        setupStep(findViewById(R.id.stepOutForDelivery), "Out for Delivery", "Our rider is on the way", isOut, true);
        
        boolean isDelivered = status.equals("Delivered");
        setupStep(findViewById(R.id.stepDelivered), "Delivered", "Enjoy your meal!", isDelivered, false);
    }

    private void setupStep(View stepView, String title, String subtitle, boolean isCompleted, boolean showBottomLine) {
        TextView titleTv = stepView.findViewById(R.id.stepTitle);
        TextView subtitleTv = stepView.findViewById(R.id.stepSubtitle);
        ImageView icon = stepView.findViewById(R.id.stepIcon);
        View lineTop = stepView.findViewById(R.id.lineTop);
        View lineBottom = stepView.findViewById(R.id.lineBottom);

        titleTv.setText(title);
        subtitleTv.setText(subtitle);

        int color = isCompleted ? 0xFFFF8F00 : 0xFFDDDDDD; // Orange if completed, Gray otherwise
        icon.setColorFilter(color);
        
        if (showBottomLine) {
            lineBottom.setVisibility(View.VISIBLE);
            lineBottom.setBackgroundColor(color);
        } else {
            lineBottom.setVisibility(View.GONE);
        }
        
        // Hide top line for the first item (though we use padding/layout usually, this is simple)
        // For simplicity in this include-based layout, we'll just manage them explicitly if needed.
    }
}