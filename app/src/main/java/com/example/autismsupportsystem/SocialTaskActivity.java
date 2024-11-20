package com.example.autismsupportsystem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;  // Correct import
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SocialTaskActivity extends AppCompatActivity {

    private CardView cardView1, cardView2, cardView3, cardView4, cardView5;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "CardViewPrefs";
    private static final long ONE_DAY_MILLIS = 24 * 60 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_social_task);

        // Initialize views
        cardView1 = findViewById(R.id.cardView1);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);
        cardView4 = findViewById(R.id.cardView4);
        cardView5 = findViewById(R.id.cardView5);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Retrieve the result score from SharedPreferences
        int resultScore = SharedPrefManager.score;

        // Determine and display the appropriate tasks based on the score
        displayCategoryCards(resultScore);

        // Restore the visibility of CardViews based on previous timestamps
        restoreCardViewVisibility(cardView1, "cardView1");
        restoreCardViewVisibility(cardView2, "cardView2");
        restoreCardViewVisibility(cardView3, "cardView3");
        restoreCardViewVisibility(cardView4, "cardView4");
        restoreCardViewVisibility(cardView5, "cardView5");

        // Set click listeners for CardViews to hide them and save timestamp
        setCardViewClickListener(cardView1, "cardView1");
        setCardViewClickListener(cardView2, "cardView2");
        setCardViewClickListener(cardView3, "cardView3");
        setCardViewClickListener(cardView4, "cardView4");
        setCardViewClickListener(cardView5, "cardView5");
    }

    private void setCardViewClickListener(CardView cardView, String key) {
        cardView.setOnClickListener(v -> {
            // Set visibility to GONE
            cardView.setVisibility(View.GONE);

            // Save current timestamp in SharedPreferences
            long currentTime = System.currentTimeMillis();
            sharedPreferences.edit().putLong(key, currentTime).apply();

            // Schedule to reappear after 24 hours
            new Handler().postDelayed(() -> runOnUiThread(() -> {
                cardView.setVisibility(View.VISIBLE);
                sharedPreferences.edit().remove(key).apply(); // Remove the timestamp
            }), ONE_DAY_MILLIS);
        });
    }

    private void restoreCardViewVisibility(CardView cardView, String key) {
        long lastClickTime = sharedPreferences.getLong(key, 0);
        if (lastClickTime > 0) {
            long elapsedTime = System.currentTimeMillis() - lastClickTime;
            if (elapsedTime < ONE_DAY_MILLIS) {
                // If 24 hours haven't passed, keep the CardView hidden
                cardView.setVisibility(View.GONE);

                // Schedule to reappear after the remaining time
                long remainingTime = ONE_DAY_MILLIS - elapsedTime;
                new Handler().postDelayed(() -> runOnUiThread(() -> {
                    cardView.setVisibility(View.VISIBLE);
                    sharedPreferences.edit().remove(key).apply(); // Remove the timestamp
                }), remainingTime);
            }
        }
    }

    private void displayCategoryCards(int score) {
        if (score >= 10 && score <= 19) {
            // Excellent Social Skills
            updateCardView(cardView1, R.id.task1, "Lead a group activity or discussion.");
            updateCardView(cardView2, R.id.task2, "Offer assistance to someone needing help with a task.");
            updateCardView(cardView3, R.id.task3, "Practice mentoring a peer or younger individual in a social skill.");
            updateCardView(cardView4, R.id.task4, "Organize a small social event or outing with friends.");
            updateCardView(cardView5, R.id.task5, "Reflect on how your social skills positively impact others by journaling.");
        } else if (score >= 20 && score <= 29) {
            // Good Social Skills
            updateCardView(cardView1, R.id.task1, "Practice active listening during a conversation with a friend or colleague.");
            updateCardView(cardView2, R.id.task2, "Initiate a conversation with someone you don’t usually talk to.");
            updateCardView(cardView3, R.id.task3, "Join a group activity and take on a supportive role.");
            updateCardView(cardView4, R.id.task4, "Compliment someone sincerely during the day.");
            updateCardView(cardView5, R.id.task5, "Participate in a discussion where you take turns speaking.");
        } else if (score >= 30 && score <= 39) {
            // Moderate Social Skills
            updateCardView(cardView1, R.id.task1, "Role-play a social situation (e.g., greeting someone) with a trusted friend or family member.");
            updateCardView(cardView2, R.id.task2, "Observe a group interaction and take notes on positive behaviors.");
            updateCardView(cardView3, R.id.task3, "Focus on maintaining eye contact during one conversation.");
            updateCardView(cardView4, R.id.task4, "Practice resolving a minor disagreement calmly and respectfully.");
            updateCardView(cardView5, R.id.task5, "Join a small group activity and contribute at least one idea.");
        } else if (score >= 40 && score <= 45) {
            // Emerging Social Skills
            updateCardView(cardView1, R.id.task1, "Start your day by practicing a friendly greeting in front of a mirror.");
            updateCardView(cardView2, R.id.task2, "Use a social cue (e.g., laughter or a nod) in response to someone’s statement.");
            updateCardView(cardView3, R.id.task3, "Ask someone a question about their day or interests.");
            updateCardView(cardView4, R.id.task4, "Focus on taking turns during a conversation or game.");
            updateCardView(cardView5, R.id.task5,"Participate in a group activity where you observe and imitate positive social behaviors.");
        } else {
            // Needs Improvement
            updateCardView(cardView1, R.id.task1, "Practice saying 'hello' or waving to at least three people during the day.");
            updateCardView(cardView2, R.id.task2,"Identify one social cue (e.g., a smile) and practice recognizing it.");
            updateCardView(cardView3, R.id.task3, "Write down a list of questions to use in conversations and try asking one.");
            updateCardView(cardView4, R.id.task4, "Play a cooperative game with a trusted person to practice turn-taking.");
            updateCardView(cardView5, R.id.task5, "Spend 5–10 minutes observing social interactions in a safe environment to learn from them.");
        }
    }

    private void updateCardView(CardView cardView, int textViewId, String taskText) {
        if (cardView != null) {
            TextView taskTextView = cardView.findViewById(textViewId); // Find the specific TextView by ID
            if (taskTextView != null) {
                taskTextView.setText(taskText); // Set the task text
            } else {
                Log.e("SocialTaskActivity", "TextView not found inside CardView");
            }
        } else {
            Log.e("SocialTaskActivity", "CardView is null");
        }
    }
}
