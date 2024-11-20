package com.example.autismsupportsystem;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class AacActivity extends AppCompatActivity {

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aac);

        // Initialize TextToSpeech
        textToSpeech = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.US);
            }
        });

        // Assign images and listeners
        setupCardImagesAndListeners();
    }

    private void setupCardImagesAndListeners() {
        // Card IDs
        String[] cardIds = {"cardEat", "cardDrink", "cardPlay", "cardSleep", "cardHappy",
                "cardSad", "cardAngry", "cardGo", "cardStop", "cardMore",
                "cardLess", "cardMom", "cardDad", "cardFriend", "cardTeacher",
                "cardHello", "cardHelp", "cardYes", "cardNo", "cardThankYou"};

        // Drawable resource names
        String[] drawableNames = {"eat", "drink", "play", "sleep", "happy",
                "sad", "angry", "go", "stop", "more",
                "less", "mom", "dad", "friend", "teacher",
                "hello", "help", "yes", "no", "thankyou"};

        // Tags (Full Sentences)
        String[] tags = {"I want to eat something", "I want to drink water", "I want to play outside", "I want to sleep now", "I am feeling happy",
                "I am feeling sad", "I am feeling angry", "I want to go outside", "Please stop this", "I want more",
                "I want less of this", "I want to see mom", "I want to see dad", "I want to play with my friend", "I want to talk to my teacher",
                "Hello, how are you?", "I need help with this", "Yes, I agree", "No, I disagree", "Thank you for your help"};

        for (int i = 0; i < cardIds.length; i++) {
            int cardResId = getResources().getIdentifier(cardIds[i], "id", getPackageName());
            int imageResId = getResources().getIdentifier(drawableNames[i], "drawable", getPackageName());

            View card = findViewById(cardResId);
            if (card != null) {
                // Set ImageView inside card
                ImageView imageView = card.findViewById(R.id.content);
                if (imageView != null) {
                    imageView.setImageResource(imageResId);
                }

                // Set the Text-to-Speech click listener
                String sentence = tags[i]; // Get the corresponding tag
                card.setOnClickListener(v -> speak(sentence));
            }
        }
    }

    // Method to speak the tag text
    private void speak(String text) {
        if (textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}

