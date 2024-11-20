package com.example.autismsupportsystem;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        // Get reference to the button
        Button blogButton = findViewById(R.id.blogButton);  // Assuming you added an ID to the button in your XML
        Button readOne = findViewById(R.id.read1);  // Assuming you added an ID to the button in your XML

        readOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the URL
                String url = "https://www.forbes.com/sites/jenniferpalumbo/2021/12/31/how-a-new-study-shows-the-importance-of-early-diagnosis-of-autistic-infants/";

                // Create an Intent to open the URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                // Start the activity (open the web browser)
                startActivity(intent);
            }
        });

        blogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the URL
                String url = "https://www.autismspeaks.org/blog?article_type[1996]=1996&article_type[1996]=1996";

                // Create an Intent to open the URL
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                // Start the activity (open the web browser)
                startActivity(intent);
            }
        });
    }
}
