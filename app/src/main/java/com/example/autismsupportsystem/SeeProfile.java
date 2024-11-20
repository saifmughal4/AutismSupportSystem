package com.example.autismsupportsystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class SeeProfile extends AppCompatActivity {

    private static final int EDIT_PROFILE_REQUEST_CODE = 1;

    private TextView userNameTextView, userEmailTextView, userKidsAgeTextView, userGenderTextView,
            userHeightTextView, userWeightTextView, userHistoryTextView, userLifestyleTextView;
    private CircleImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_profile);

        profileImageView = findViewById(R.id.profileImageView);
        userNameTextView = findViewById(R.id.textViewUserName);
        userEmailTextView = findViewById(R.id.textViewUserEmail);
        userKidsAgeTextView = findViewById(R.id.textViewUserKidsAge);
        userGenderTextView = findViewById(R.id.textViewUserGender);
        userHeightTextView = findViewById(R.id.textViewUserHeight);
        userWeightTextView = findViewById(R.id.textViewUserWeight);
        userHistoryTextView = findViewById(R.id.textViewUserHistory);
        userLifestyleTextView = findViewById(R.id.textViewUserLifestyle);
        Button editProfileButton = findViewById(R.id.editProfileButton);

        // Retrieve user data from SharedPreferences
        User user = SharedPrefManager.getInstance(this).getUser();

        // Set user data in views
        userNameTextView.setText(user.name);
        userEmailTextView.setText("Email: " + user.email);
        userKidsAgeTextView.setText("Child's Age: " + user.kidsAge);
        userGenderTextView.setText("Gender: " + user.gender);
        userHeightTextView.setText("Height: " + user.height);
        userWeightTextView.setText("Weight: " + user.weight);
        userHistoryTextView.setText("History: " + user.history);
        userLifestyleTextView.setText("Lifestyle: " + user.lifestyle);

        // Load the user's profile image (if available)
        if (user.image != null && !user.image.isEmpty()) {
            profileImageView.setImageBitmap(Utils.decodeBase64ToBitmap(user.image));
        }

        // Set edit profile button click listener
        editProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(SeeProfile.this, EditProfile.class);
            startActivityForResult(intent, EDIT_PROFILE_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_PROFILE_REQUEST_CODE && resultCode == RESULT_OK) {
            // Update the UI with updated user data from SharedPreferences
            User updatedUser = SharedPrefManager.getInstance(this).getUser();
            userNameTextView.setText(updatedUser.name);
            userEmailTextView.setText("Email: " + updatedUser.email);
            userKidsAgeTextView.setText("Child's Age: " + updatedUser.kidsAge);
            userGenderTextView.setText("Gender: " + updatedUser.gender);
            userHeightTextView.setText("Height: " + updatedUser.height);
            userWeightTextView.setText("Weight: " + updatedUser.weight);
            userHistoryTextView.setText("History: " + updatedUser.history);
            userLifestyleTextView.setText("Lifestyle: " + updatedUser.lifestyle);

            if (updatedUser.image != null && !updatedUser.image.isEmpty()) {
                profileImageView.setImageBitmap(Utils.decodeBase64ToBitmap(updatedUser.image));
            }
        }
    }
}
