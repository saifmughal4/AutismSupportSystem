package com.example.autismsupportsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class Utils {

    // SharedPreferences key constants
    private static final String PREF_NAME = "AutismSupportPrefs";
    private static final String KEY_RESULT_SCORE = "resultScore";

    /**
     * Decodes a Base64 encoded string to a Bitmap.
     * @param encodedString The Base64 encoded string of the image.
     * @return The decoded Bitmap image.
     */
    public static Bitmap decodeBase64ToBitmap(String encodedString) {
        byte[] decodedBytes = Base64.decode(encodedString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    /**
     * Encodes a Bitmap into a Base64 string.
     * @param bitmap The Bitmap to encode.
     * @return The Base64 encoded string of the Bitmap.
     */
    public static String encodeBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    /**
     * Scales a Bitmap to the specified width and height.
     * @param bitmap The original Bitmap.
     * @param width The desired width.
     * @param height The desired height.
     * @return The scaled Bitmap.
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, int width, int height) {
        return Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    /**
     * Store the result score in SharedPreferences.
     * @param context The application context.
     * @param score The score to store.
     */
    public static void saveResultScore(Context context, int score) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_RESULT_SCORE, score);
        editor.apply(); // Save the score asynchronously
    }

    /**
     * Get the result score from SharedPreferences.
     * @param context The application context.
     * @return The stored score.
     */
    public static int getResultScoreFromPrefs(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_RESULT_SCORE, 0); // Default value is 0
    }
}
