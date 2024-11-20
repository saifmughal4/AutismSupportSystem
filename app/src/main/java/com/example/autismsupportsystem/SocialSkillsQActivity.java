package com.example.autismsupportsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SocialSkillsQActivity extends AppCompatActivity {

    private TextView questionCounter, questionText;
    private RadioGroup optionGroup;

    private String[] questions = {
            "Do you greet others when meeting them (e.g., saying \"hello\" or waving)?",
            "Do you maintain eye contact during conversations?",
            "Do you take turns while speaking in conversations or group activities?",
            "Do you express your feelings or needs clearly to others?",
            "Do you ask others questions about their interests or feelings?",
            "Do you listen attentively when someone else is speaking?",
            "Do you handle disagreements calmly without getting upset?",
            "Do you participate in group activities or social gatherings?",
            "Do you offer help or support when someone else needs it?",
            "Do you respond appropriately to social cues, such as laughter or gestures?"
    };

    private String[] options = {"Never", "Rarely", "Sometimes", "Often", "Always"};
    private int[] optionPoints = {1, 2, 3, 4, 5};

    private int currentQuestionIndex = 0;
    private int totalPoints = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_skills_qactivity);

        questionCounter = findViewById(R.id.questionCounter);
        questionText = findViewById(R.id.questionText);
        optionGroup = findViewById(R.id.optionGroup);

        findViewById(R.id.backArrow).setOnClickListener(v -> finish());

        updateQuestion();

        findViewById(R.id.btn_next).setOnClickListener(v -> {
            if (validateSelection()) {
                calculatePoints();
                currentQuestionIndex++;
                if (currentQuestionIndex < questions.length) {
                    updateQuestion();
                } else {
                    Intent intent = new Intent(SocialSkillsQActivity.this, ResultActivitySS.class);
                    intent.putExtra("totalPoints", totalPoints);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void updateQuestion() {
        questionCounter.setText((currentQuestionIndex + 1) + " of " + questions.length);
        questionText.setText(questions[currentQuestionIndex]);

        optionGroup.clearCheck();
        for (int i = 0; i < options.length; i++) {
            RadioButton rb = (RadioButton) optionGroup.getChildAt(i);
            if (rb != null) {
                rb.setText(options[i]);
            }
        }
    }

    private boolean validateSelection() {
        if (optionGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void calculatePoints() {
        int selectedId = optionGroup.getCheckedRadioButtonId();
        int selectedIndex = optionGroup.indexOfChild(findViewById(selectedId));

        if (selectedIndex >= 0 && selectedIndex < optionPoints.length) {
            totalPoints += optionPoints[selectedIndex];
        }
    }
}
