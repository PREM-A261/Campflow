package com.example.campflow;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText editName, editEmail, editMobile, editUniversityId;
    private Button btnUpdate, btnLogout;
    private View btnEditProfile;
    private ImageView profileImage, btnChangePicture;
    
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private String userId;

    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    profileImage.setImageURI(imageUri);
                    // In a real app, you would upload this to Firebase Storage
                    saveProfilePictureUri(imageUri.toString());
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        userId = user.getUid();

        // Initialize Views
        profileImage = findViewById(R.id.profileImage);
        btnChangePicture = findViewById(R.id.btnChangePicture);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editMobile = findViewById(R.id.editMobile);
        editUniversityId = findViewById(R.id.editUniversityId);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnLogout = findViewById(R.id.btnLogout);

        // Load User Data
        loadUserData();

        // Edit Profile Click
        btnEditProfile.setOnClickListener(v -> toggleEditMode(true));

        // Update Button Click
        btnUpdate.setOnClickListener(v -> updateProfile());

        // Change Profile Picture
        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickImageLauncher.launch(intent);
        });

        // Payment Methods Click
        findViewById(R.id.sectionPayment).setOnClickListener(v -> showPaymentDialog());

        // Rate Us Click
        findViewById(R.id.sectionRateUs).setOnClickListener(v -> showRatingDialog());

        // Logout
        btnLogout.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finishAffinity();
        });
    }

    private void loadUserData() {
        mDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    editName.setText(snapshot.child("name").getValue(String.class));
                    editEmail.setText(snapshot.child("email").getValue(String.class));
                    editMobile.setText(snapshot.child("mobile").getValue(String.class));
                    editUniversityId.setText(snapshot.child("universityId").getValue(String.class));
                    
                    String photoUrl = snapshot.child("profilePic").getValue(String.class);
                    if (photoUrl != null && !photoUrl.isEmpty()) {
                        profileImage.setImageURI(Uri.parse(photoUrl));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void toggleEditMode(boolean enabled) {
        editName.setEnabled(enabled);
        editEmail.setEnabled(enabled);
        editMobile.setEnabled(enabled);
        editUniversityId.setEnabled(enabled);
        
        btnUpdate.setVisibility(enabled ? View.VISIBLE : View.GONE);
        btnChangePicture.setVisibility(enabled ? View.VISIBLE : View.GONE);
        btnEditProfile.setVisibility(enabled ? View.GONE : View.VISIBLE);
        
        if (enabled) {
            editName.requestFocus();
        }
    }

    private void updateProfile() {
        String name = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String mobile = editMobile.getText().toString().trim();
        String universityId = editUniversityId.getText().toString().trim();

        Map<String, Object> updates = new HashMap<>();
        updates.put("name", name);
        updates.put("email", email);
        updates.put("mobile", mobile);
        updates.put("universityId", universityId);

        mDatabase.child("users").child(userId).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    toggleEditMode(false);
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Update Failed", Toast.LENGTH_SHORT).show());
    }

    private void saveProfilePictureUri(String uri) {
        mDatabase.child("users").child(userId).child("profilePic").setValue(uri);
    }

    private void showPaymentDialog() {
        String[] payments = {"Google Pay", "PhonePe", "Credit/Debit Card", "Net Banking"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Payment Method");
        builder.setItems(payments, (dialog, which) -> {
            Toast.makeText(this, "Selected: " + payments[which], Toast.LENGTH_SHORT).show();
        });
        builder.show();
    }

    private void showRatingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_rating, null);
        builder.setView(dialogView);
        
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBar);
        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.btnSubmitRating).setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            mDatabase.child("ratings").child(userId).setValue(rating)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Thank you for rating!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    });
        });
        
        dialog.show();
    }
}