package com.example.autismsupportsystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreen extends AppCompatActivity {


    private CircleImageView profileImageButton;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    View sensoryCard,AACCard,ComCard,AboutCard;
    TextView BodyText,HeaderText;
    CardView BodyImageCardView;
    ImageView BodyImage,headerIcon;
    private User currentUser;
    private View blogCard1, blogCard2, blogCard3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Initialize sensory card and its components
        sensoryCard = findViewById(R.id.sensory_support);
        BodyText = sensoryCard.findViewById(R.id.bodyText);
        HeaderText = sensoryCard.findViewById(R.id.headerTitle);
        HeaderText.setText("Sensory Support");
        BodyText.setText("Let's diagnose sensory conditions!");
        BodyImageCardView = sensoryCard.findViewById(R.id.body_image_card);
        BodyImage = BodyImageCardView.findViewById(android.R.id.content);
        headerIcon=sensoryCard.findViewById(R.id.headerIcon);
        headerIcon.setImageResource(R.drawable.sensorial);

        AACCard = findViewById(R.id.aac_card);
        BodyText = AACCard.findViewById(R.id.bodyText);
        HeaderText = AACCard.findViewById(R.id.headerTitle);
        HeaderText.setText("AAC");
        BodyText.setText("Let's speek by me!");
        BodyImageCardView = AACCard.findViewById(R.id.body_image_card);
        BodyImage = BodyImageCardView.findViewById(R.id.card_image);
        BodyImage.setImageResource(R.drawable.aac_imge);
        headerIcon=AACCard.findViewById(R.id.headerIcon);
        headerIcon.setImageResource(R.drawable.messenger);


        ComCard = findViewById(R.id.communication_card);
        BodyText = ComCard.findViewById(R.id.bodyText);
        HeaderText = ComCard.findViewById(R.id.headerTitle);
        HeaderText.setText("Social Skil");
        BodyText.setText("Let's make the comunication better!");
        BodyImageCardView = ComCard.findViewById(R.id.body_image_card);
        BodyImage = BodyImageCardView.findViewById(R.id.card_image);
        BodyImage.setImageResource(R.drawable.communication_img);
        headerIcon=ComCard.findViewById(R.id.headerIcon);
        headerIcon.setImageResource(R.drawable.community);

        AboutCard = findViewById(R.id.about_card);
        BodyText = AboutCard.findViewById(R.id.bodyText);
        HeaderText = AboutCard.findViewById(R.id.headerTitle);
        HeaderText.setText("Information");
        BodyText.setText("What is Austism?");
        BodyImageCardView = AboutCard.findViewById(R.id.body_image_card);
        BodyImage = BodyImageCardView.findViewById(R.id.card_image);
        BodyImage.setImageResource(R.drawable.about_card);
        headerIcon=AboutCard.findViewById(R.id.headerIcon);
        headerIcon.setImageResource(R.drawable.info);

        blogCard1 = findViewById(R.id.blog_card_1);
        blogCard2 = findViewById(R.id.blog_card_2);
        blogCard3 = findViewById(R.id.blog_card_3);



        setBlogCardData(
                blogCard1,
                "One day they will join us in the sun",
                "This article is about the ground-breaking research: ‘Autistic Masking: Understanding the narrative of Stigma and the Illusion of Choice’ by Kieran Rose & Dr Amy Pearson."
        );

        setBlogCardData(
                blogCard2,
                "An Autistic Burnout",
                "In this article I will discuss how to recognise and understand Autistic Burnout in children and adults as an Autistic person, professional, parent or carer.\n\nIt’s taken me six weeks to start writing an article about Autistic Burnout, because I’m going through Autistic Burnout…"
        );

        setBlogCardData(
                blogCard3,
                "Why I love my community",
                "Sometimes, despite my best efforts, I’m not able to achieve things on my own. I have to throw things out into the universe and hope that others step up to the plate to back me up. Yesterday, this happened beautifully after I emailed my mailing list about some new research I’m involved with."
        );

        blogCard1.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://theautisticadvocate.com/one-day-they-will-join-us-in-the-sun/"));
            startActivity(intent);
        });
        blogCard2.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://theautisticadvocate.com/an-autistic-burnout/"));
            startActivity(intent);
        });
        blogCard3.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://theautisticadvocate.com/why-i-love-my-community/"));
            startActivity(intent);
        });



        sensoryCard.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, SensoryQ.class);
            startActivity(intent);
        });
        AACCard.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, AacActivity.class);
            startActivity(intent);
        });
        ComCard.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, SocialSkillsActivity.class);
            startActivity(intent);
        });
        AboutCard.setOnClickListener(v -> {
            Intent intent = new Intent(HomeScreen.this, About.class);
            startActivity(intent);
        });


        // Initialize profileImageButton and Firebase setup
        profileImageButton = findViewById(R.id.profileImageButton);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            String userId = firebaseUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("users");

            databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        currentUser = dataSnapshot.getValue(User.class);

                        if (currentUser != null) {
                            displayUserData();
                            SharedPrefManager.getInstance(HomeScreen.this).saveUser(currentUser);

                            profileImageButton.setOnClickListener(v -> showProfileMenu(currentUser));
                        }
                    } else {
                        Toast.makeText(HomeScreen.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("HomeScreen", "Error fetching user data", databaseError.toException());
                }
            });
        }
    }

    private void setBlogCardData(View blogCard, String header, String bodyText) {
        TextView headerTextView = blogCard.findViewById(R.id.headerTitle);
        TextView bodyTextView = blogCard.findViewById(R.id.bodyText);

        headerTextView.setText(header);
        bodyTextView.setText(bodyText);

        // Optionally set an image for the blog card
        ImageView headerIcon = blogCard.findViewById(R.id.headerIcon);
        headerIcon.setImageResource(R.drawable.logo); // Replace with actual drawable resource if available
    }

    private void displayUserData() {
        // Display welcome text

        // Decode and set profile picture
        if (currentUser.image != null && !currentUser.image.isEmpty()) {
            Bitmap profileBitmap = decodeBase64ToBitmap(currentUser.image);
            profileImageButton.setImageBitmap(profileBitmap);
        } else {
            profileImageButton.setImageResource(R.drawable.pic_place_holder);
        }
    }



    private void showProfileMenu(User user) {
        PopupMenu popupMenu = new PopupMenu(this, profileImageButton);
        popupMenu.getMenuInflater().inflate(R.menu.profile_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            int itemId = menuItem.getItemId();

            if (itemId == R.id.menuSeeProfile) {
                // Pass User object to SeeProfile Activity
                Intent intent = new Intent(HomeScreen.this, SeeProfile.class);
                startActivity(intent);

                return true;
            } else if (itemId == R.id.menuEditProfile) {
                // Pass User object to EditProfile Activity
                Intent intent = new Intent(HomeScreen.this, EditProfile.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

        popupMenu.show();
    }

    private Bitmap decodeBase64ToBitmap(String base64String) {
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}
