package com.example.autismsupportsystem;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivitySS extends AppCompatActivity {

    private TextView resultText, categoryText, recommendationText;
    private ProgressBar scoreProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_ss);

        // Initialize the views
        resultText = findViewById(R.id.resultText);
        categoryText = findViewById(R.id.categoryText);
        recommendationText = findViewById(R.id.recommendationText);
        scoreProgressBar = findViewById(R.id.scoreProgressBar);

        // Get the total points from the Intent
        int totalPoints = getIntent().getIntExtra("totalPoints", 0);

        // Set the result score
        resultText.setText(String.valueOf(totalPoints));
        SharedPrefManager.score = totalPoints;

        // Log the total points to ensure they are being retrieved correctly
        Log.d("ResultActivitySS", "Total points: " + totalPoints);

        // Set the progress bar value based on the total points
        scoreProgressBar.setMax(50);
        scoreProgressBar.setProgress(totalPoints);

        // Categorize the score and display appropriate category and recommendations
        String category;
        String recommendations;

        if (totalPoints <= 19) {
            category = "Excellent Social Skills";
            recommendations = "You demonstrate strong social skills and engage effectively with others. Continue to maintain positive interactions and be a role model in social settings.";
        } else if (totalPoints <= 29) {
            category = "Good Social Skills";
            recommendations = "You have good social skills but may benefit from practicing active listening or engaging more in group settings. Focus on improving one or two areas for even stronger connections.";
        } else if (totalPoints <= 39) {
            category = "Moderate Social Skills";
            recommendations = "Your social skills are developing, but you may face occasional challenges in areas like turn-taking or responding to social cues. Try practicing through role-playing or joining group activities.";
        } else if (totalPoints <= 45) {
            category = "Emerging Social Skills";
            recommendations = "You are beginning to build social skills but need support in managing social cues, expressing yourself, or resolving conflicts. Work on small, manageable goals, and seek guidance if needed.";
        } else {
            category = "Needs Improvement";
            recommendations = "You may find social interactions challenging. Start with simple steps, like practicing greetings or asking questions, and seek structured social skills training or group therapy for improvement.";
        }

        // Set the category text and recommendations
        categoryText.setText(category);
        recommendationText.setText(recommendations);

        // Save the score using the SharedPreferences method (or static method if you prefer)
        Utils.saveResultScore(this, totalPoints); // Save in SharedPreferences
        // Log score saving for debugging
        Log.d("ResultActivitySS", "Score saved to SharedPreferences: " + totalPoints);
    }
}
