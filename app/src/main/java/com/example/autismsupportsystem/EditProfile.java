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

public class EditProfile extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText, kidsAgeEditText, genderEditText, heightEditText, weightEditText, historyEditText, lifestyleEditText;
    private Button signupButton, selectImageButton;
    private ImageView selectedImageView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String encodedImage;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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

        // Get the current user data from Shared Preferences
        User user = SharedPrefManager.getInstance(this).getUser();

        // Initialize FirebaseAuth and DatabaseReference
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Pre-fill the fields with the current user data
        nameEditText.setText(user.name);
        emailEditText.setText(user.email);
        kidsAgeEditText.setText(user.kidsAge);
        genderEditText.setText(user.gender);
        heightEditText.setText(user.height);
        weightEditText.setText(user.weight);
        historyEditText.setText(user.history);
        lifestyleEditText.setText(user.lifestyle);

        // Set up listeners for buttons
        selectImageButton.setOnClickListener(v -> openGallery());
        signupButton.setOnClickListener(v -> updateUser());
    }

    private void openGallery() {
        // Open the gallery to select an image
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
        // Convert Bitmap image to Base64 string
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void updateUser() {
        // Get updated data from the user input fields
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

        // Validate that no required fields are empty
        if (name.isEmpty() || email.isEmpty() || kidsAge.isEmpty() || gender.isEmpty() ||
                height.isEmpty() || weight.isEmpty() || history.isEmpty() || lifestyle.isEmpty() || encodedImage == null) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the passwords match
        if (!password.isEmpty() && !password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();

            // Create the updated User object
            User updatedUser = new User(name, email, kidsAge, gender, height, weight, history, lifestyle, encodedImage);

            // Update user data in Firebase Realtime Database
            databaseReference.child(userId).setValue(updatedUser)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Check if the email is updated
                            if (!firebaseUser.getEmail().equals(email)) {
                                firebaseUser.updateEmail(email)
                                        .addOnCompleteListener(emailTask -> {
                                            if (!emailTask.isSuccessful()) {
                                                Toast.makeText(EditProfile.this, "Failed to update email", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                            // If password is provided, update it as well
                            if (!password.isEmpty()) {
                                firebaseUser.updatePassword(password)
                                        .addOnCompleteListener(passwordTask -> {
                                            if (!passwordTask.isSuccessful()) {
                                                Toast.makeText(EditProfile.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                            // Show success message
                            Toast.makeText(EditProfile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();

                            // Redirect to the ProfileActivity to see the updated profile
                            Intent intent = new Intent(EditProfile.this, EditProfile.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(EditProfile.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No user is logged in", Toast.LENGTH_SHORT).show();
        }
    }
}
