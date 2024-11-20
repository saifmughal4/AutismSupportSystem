package com.example.autismsupportsystem;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    private TextView resultText, categoryText, recommendationText;
    private ProgressBar scoreProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize the views
        resultText = findViewById(R.id.resultText);
        categoryText = findViewById(R.id.categoryText);
        recommendationText = findViewById(R.id.recommendationText);
        scoreProgressBar = findViewById(R.id.scoreProgressBar);

        // Get the total points from the Intent
        int totalPoints = getIntent().getIntExtra("totalPoints", 0);

        // Set the result score
        resultText.setText(""+totalPoints);

        // Set the progress bar value based on the total points
        scoreProgressBar.setProgress(totalPoints);

        // Categorize the score and display appropriate category and recommendations
        String category;
        String recommendations;

        if (totalPoints >= 70) {
            category = "High Sensory Sensitivity";
            recommendations = "Significant sensory processing challenges may affect daily life. " +
                    "Consider using weighted items, noise-reduction strategies, and controlled movement exercises. " +
                    "Professional guidance, such as occupational therapy, may be beneficial for personalized support.";
        } else if (totalPoints >= 40) {
            category = "Moderate Sensory Sensitivity ";
            recommendations = "The individual experiences occasional sensory difficulties. Introduce sensory-rich activities " +
                    "such as textured toys, noise-reduction strategies, and movement exercises to help manage sensitivities in " +
                    "areas like touch, sound, and balance.";
        } else {
            category = "Low Sensory Sensitivity";
            recommendations = "The individual exhibits typical sensory processing with minimal challenges. No significant interventions " +
                    "are needed, but regular sensory activities like play, art, or exercise can help maintain balance and overall sensory health.";
        }

        // Set the category text
        categoryText.setText(category);

        // Set the recommendations text
        recommendationText.setText(recommendations);
    }
}
