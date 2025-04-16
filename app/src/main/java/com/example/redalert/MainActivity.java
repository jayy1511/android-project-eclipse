package com.example.redalert;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        // 🔐 Redirect to Login if user is not logged in
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        // 📚 View Health Articles
        Button btnViewArticles = findViewById(R.id.btnViewArticles);
        btnViewArticles.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, HealthArticlesActivity.class));
        });

        // 🔮 Predict Period
        Button btnStartCycle = findViewById(R.id.btnStartCycle);
        btnStartCycle.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, StartCycleActivity.class));
        });

        // 💬 Daily Quote
        Button btnDailyQuote = findViewById(R.id.btnDailyQuote);
        btnDailyQuote.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, DailyQuoteActivity.class));
        });

        // 🔔 Reminder
        Button btnReminder = findViewById(R.id.btnReminder);
        btnReminder.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ReminderActivity.class));
        });

        // 👤 User Profile
        Button btnUserProfile = findViewById(R.id.btnUserProfile);
        btnUserProfile.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, UserProfileActivity.class));
        });

        // 🚪 Logout
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }
}
