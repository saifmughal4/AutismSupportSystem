package com.example.autismsupportsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SensoryQ extends AppCompatActivity {

    // Views
    private TextView questionCounter, questionText;
    private RadioGroup optionGroup;
    private Button nextButton;

    // Questions and options
    private String[] questions = {
            "Does the individual avoid touching certain textures (e.g., fabric, sand, or sticky substances)?",
            "Does the individual show discomfort when wearing certain clothing (e.g., tags, tight clothing)?",
            "Does the individual overreact to light touches or accidental bumps?",
            "Does the individual seek out firm pressure (e.g., hugs, squeezing)?",
            "Does the individual cover their ears or show distress in noisy environments?",
            "Does the individual appear overly sensitive to certain sounds (e.g., alarms, music)?",
            "Does the individual seek out or enjoy repetitive sounds?",
            "Does the individual fail to respond to sounds others typically notice?",
            "Does the individual avoid bright lights or shield their eyes?",
            "Does the individual focus on small details or patterns instead of the big picture?",
            "Does the individual appear fascinated by moving or spinning objects?",
            "Does the individual avoid making eye contact?",
            "Does the individual appear clumsy or have difficulty with coordinated movements?",
            "Does the individual seek deep pressure, such as squeezing or pushing heavy objects?",
            "Does the individual show excessive force when handling objects or interacting with others?",
            "Does the individual avoid activities requiring precise motor control (e.g., writing, puzzles)?",
            "Does the individual avoid activities involving motion (e.g., swings, car rides)?",
            "Does the individual appear overly active or always on the move?",
            "Does the individual enjoy spinning or rocking motions excessively?",
            "Does the individual show difficulty maintaining balance or posture?"
    };

    private String[] options = {"Never", "Rarely", "Sometimes", "Often", "Always"};
    private int[] optionPoints = {1, 2, 3, 4, 5};

    private int currentQuestionIndex = 0;
    private int totalPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensory_q);

        // Initialize views
        questionCounter = findViewById(R.id.questionCounter);
        questionText = findViewById(R.id.questionText);
        optionGroup = findViewById(R.id.optionGroup);
        nextButton = findViewById(R.id.btn_next);

        // Display the first question
        updateQuestion();

        // Back arrow functionality
        findViewById(R.id.backArrow).setOnClickListener(v -> finish());

        // Next button click listener
        nextButton.setOnClickListener(v -> {
            if (validateSelection()) {
                calculatePoints();
                currentQuestionIndex++;

                if (currentQuestionIndex < questions.length) {
                    updateQuestion();
                } else {
                    // Finish quiz and navigate to the result screen
                    Intent resultIntent = new Intent(SensoryQ.this, ResultActivity.class);
                    resultIntent.putExtra("totalPoints", totalPoints);
                    startActivity(resultIntent);
                    finish();
                }
            }
        });
    }

    /**
     * Updates the UI with the current question and resets the options.
     */
    private void updateQuestion() {
        // Update question counter and text
        questionCounter.setText((currentQuestionIndex + 1) + " of " + questions.length);
        questionText.setText(questions[currentQuestionIndex]);

        // Update options text dynamically
        optionGroup.clearCheck(); // Clear previous selection
        for (int i = 0; i < options.length; i++) {
            RadioButton radioButton = (RadioButton) optionGroup.getChildAt(i);
            if (radioButton != null) {
                radioButton.setText(options[i]);
            }
        }
    }

    /**
     * Validates if an option is selected.
     *
     * @return true if a selection is made, false otherwise
     */
    private boolean validateSelection() {
        if (optionGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Calculates points based on the selected option.
     */
    private void calculatePoints() {
        int selectedId = optionGroup.getCheckedRadioButtonId();
        View selectedView = findViewById(selectedId);
        int selectedIndex = optionGroup.indexOfChild(selectedView);

        if (selectedIndex >= 0 && selectedIndex < optionPoints.length) {
            totalPoints += optionPoints[selectedIndex];
        }
    }
}
