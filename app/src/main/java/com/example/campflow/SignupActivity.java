package com.example.campflow;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private TextInputEditText nameEdit, emailEdit, passwordEdit;
    private Button btnSignup;
    private TextView txtLogin;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nameEdit = findViewById(R.id.nameEdit);
        emailEdit = findViewById(R.id.emailEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        btnSignup = findViewById(R.id.btnSignup);
        txtLogin = findViewById(R.id.txtLogin);

        btnSignup.setOnClickListener(v -> {
            String email = emailEdit.getText().toString().trim();
            String password = passwordEdit.getText().toString().trim();
            String name = nameEdit.getText().toString().trim();

            if (TextUtils.isEmpty(name)) {
                nameEdit.setError("Name is required");
                return;
            }

            if (TextUtils.isEmpty(email)) {
                emailEdit.setError("Email is required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                passwordEdit.setError("Password is required");
                return;
            }

            if (password.length() < 6) {
                passwordEdit.setError("Password must be at least 6 characters");
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String userId = mAuth.getCurrentUser().getUid();
                            
                            Map<String, Object> userMap = new HashMap<>();
                            userMap.put("name", name);
                            userMap.put("email", email);
                            userMap.put("mobile", "");
                            userMap.put("universityId", "");
                            userMap.put("profilePic", "");

                            mDatabase.child("users").child(userId).setValue(userMap)
                                    .addOnCompleteListener(dbTask -> {
                                        if (dbTask.isSuccessful()) {
                                            Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                            finishAffinity();
                                        } else {
                                            String error = dbTask.getException() != null ? dbTask.getException().getMessage() : "Unknown error";
                                            Log.e(TAG, "Database Error: " + error);
                                            Toast.makeText(SignupActivity.this, "Auth success, but failed to save data. Check Firebase Rules.", Toast.LENGTH_LONG).show();
                                            // Proceed to MainActivity anyway so they can at least use the app
                                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                            finishAffinity();
                                        }
                                    });
                        } else {
                            Toast.makeText(SignupActivity.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        txtLogin.setOnClickListener(v -> finish());
    }
}