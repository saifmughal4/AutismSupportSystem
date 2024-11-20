package com.example.autismsupportsystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Register extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText, kidsAgeEditText, genderEditText, heightEditText, weightEditText, historyEditText, lifestyleEditText;
    private Button signupButton, selectImageButton;
    private ImageView selectedImageView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private String encodedImage; // Store the encoded image string

    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.CPasswordEditText);
        kidsAgeEditText = findViewById(R.id.kidAgeEditText);
        genderEditText = findViewById(R.id.kidGenderEditText);
        heightEditText = findViewById(R.id.kidHeightEditText);
        weightEditText = findViewById(R.id.kidWeightEditText);
        historyEditText = findViewById(R.id.kidHistoryEditText);
        lifestyleEditText = findViewById(R.id.kidLifestyleEditText);
        signupButton = findViewById(R.id.signupButton);
        selectImageButton = findViewById(R.id.selectPhotoButton);
        selectedImageView = findViewById(R.id.kidImageView);

        // Set up listeners
        selectImageButton.setOnClickListener(v -> openGallery());
        signupButton.setOnClickListener(v -> registerUser());
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                selectedImageView.setImageBitmap(bitmap);
                encodedImage = encodeImageToBase64(bitmap); // Convert the image to Base64
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream); // Compress the image
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void registerUser() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String kidsAge = kidsAgeEditText.getText().toString().trim();
        String gender = genderEditText.getText().toString().trim();
        String height = heightEditText.getText().toString().trim();
        String weight = weightEditText.getText().toString().trim();
        String history = historyEditText.getText().toString().trim();
        String lifestyle = lifestyleEditText.getText().toString().trim();

        // Validate input fields
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || kidsAge.isEmpty()
                || gender.isEmpty() || height.isEmpty() || weight.isEmpty() || history.isEmpty() || lifestyle.isEmpty() || encodedImage == null) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register user with Firebase Authentication
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String userId = firebaseUser.getUid();
                            User user = new User(name, email, kidsAge, gender, height, weight, history, lifestyle, encodedImage);
                            databaseReference.child(userId).setValue(user)
                                    .addOnCompleteListener(databaseTask -> {
                                        if (databaseTask.isSuccessful()) {
                                            Toast.makeText(Register.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Register.this, MainActivity.class));
                                            finish();
                                        } else {
                                            Toast.makeText(Register.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(Register.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}


