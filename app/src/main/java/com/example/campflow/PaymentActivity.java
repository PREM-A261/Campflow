package com.example.campflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class PaymentActivity extends AppCompatActivity {

    private RadioButton rbUPI, rbCard, rbNetBanking;
    private LinearLayout layoutQR, layoutCardDetails;
    private Spinner spinnerBanks;
    private Button btnPayNow;
    private MaterialCardView cardUPI, cardCard, cardNetBanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        rbUPI = findViewById(R.id.rbUPI);
        rbCard = findViewById(R.id.rbCard);
        rbNetBanking = findViewById(R.id.rbNetBanking);

        layoutQR = findViewById(R.id.layoutQR);
        layoutCardDetails = findViewById(R.id.layoutCardDetails);
        spinnerBanks = findViewById(R.id.spinnerBanks);

        cardUPI = findViewById(R.id.cardUPI);
        cardCard = findViewById(R.id.cardCard);
        cardNetBanking = findViewById(R.id.cardNetBanking);

        btnPayNow = findViewById(R.id.btnPayNow);

        // Click listeners for RadioButtons
        rbUPI.setOnClickListener(v -> selectUPI());
        rbCard.setOnClickListener(v -> selectCard());
        rbNetBanking.setOnClickListener(v -> selectNetBanking());

        // Click listeners for the CardViews themselves for better UX
        cardUPI.setOnClickListener(v -> selectUPI());
        cardCard.setOnClickListener(v -> selectCard());
        cardNetBanking.setOnClickListener(v -> selectNetBanking());

        btnPayNow.setOnClickListener(v -> {
            if (!rbUPI.isChecked() && !rbCard.isChecked() && !rbNetBanking.isChecked()) {
                Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
                return;
            }
            
            // Logic for specific validations could go here
            if (rbCard.isChecked()) {
                // Example: check if card fields are filled
            }

            // Navigate to SuccessActivity instead of just showing a Toast
            Intent intent = new Intent(PaymentActivity.this, SuccessActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void selectUPI() {
        rbUPI.setChecked(true);
        rbCard.setChecked(false);
        rbNetBanking.setChecked(false);
        updateVisibility();
    }

    private void selectCard() {
        rbUPI.setChecked(false);
        rbCard.setChecked(true);
        rbNetBanking.setChecked(false);
        updateVisibility();
    }

    private void selectNetBanking() {
        rbUPI.setChecked(false);
        rbCard.setChecked(false);
        rbNetBanking.setChecked(true);
        updateVisibility();
    }

    private void updateVisibility() {
        layoutQR.setVisibility(rbUPI.isChecked() ? View.VISIBLE : View.GONE);
        layoutCardDetails.setVisibility(rbCard.isChecked() ? View.VISIBLE : View.GONE);
        spinnerBanks.setVisibility(rbNetBanking.isChecked() ? View.VISIBLE : View.GONE);
        
        // Update card strokes to highlight selection
        cardUPI.setStrokeWidth(rbUPI.isChecked() ? 4 : 1);
        cardCard.setStrokeWidth(rbCard.isChecked() ? 4 : 1);
        cardNetBanking.setStrokeWidth(rbNetBanking.isChecked() ? 4 : 1);
    }
}