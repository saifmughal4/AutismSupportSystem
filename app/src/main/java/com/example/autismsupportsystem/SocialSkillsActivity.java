package com.example.autismsupportsystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SocialSkillsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_skills);

        // Card for AAC Activities
        findViewById(R.id.cardAacActivity).setOnClickListener(v -> {
            Intent intent = new Intent(SocialSkillsActivity.this, AacActivity.class);
            startActivity(intent);
        });

        // Card for Social Skills Analysis
        findViewById(R.id.cardSocialAnalysis).setOnClickListener(v -> {
            Intent intent = new Intent(SocialSkillsActivity.this, SocialSkillsQActivity.class);
            startActivity(intent);
        });

        // Card for Social Tasks
        findViewById(R.id.cardSocialTask).setOnClickListener(v -> {
            Intent intent = new Intent(SocialSkillsActivity.this, SocialTaskActivity.class);
            startActivity(intent);
        });

        // Card for Social Training (YouTube Link)
        findViewById(R.id.cardSocialTraining).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=dzD7YfEfgs4&ab_channel=BrendonBurchard"));
            startActivity(intent);
        });
    }
}
