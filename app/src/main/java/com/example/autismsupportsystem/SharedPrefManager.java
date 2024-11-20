package com.example.autismsupportsystem;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "user_shared_pref";
    private static SharedPrefManager instance;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public  static int score;

    private SharedPrefManager(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void saveUser(User user) {
        editor.putString("name", user.name);
        editor.putString("email", user.email);
        editor.putString("kidsAge", user.kidsAge);
        editor.putString("gender", user.gender);
        editor.putString("height", user.height);
        editor.putString("weight", user.weight);
        editor.putString("history", user.history);
        editor.putString("lifestyle", user.lifestyle);
        editor.putString("encodedImage", user.image);
        editor.apply();
    }

    public User getUser() {
        return new User(
                sharedPreferences.getString("name", ""),
                sharedPreferences.getString("email", ""),
                sharedPreferences.getString("kidsAge", ""),
                sharedPreferences.getString("gender", ""),
                sharedPreferences.getString("height", ""),
                sharedPreferences.getString("weight", ""),
                sharedPreferences.getString("history", ""),
                sharedPreferences.getString("lifestyle", ""),
                sharedPreferences.getString("encodedImage", "")
        );
    }

    public void clear() {
        editor.clear().apply();
    }
}
