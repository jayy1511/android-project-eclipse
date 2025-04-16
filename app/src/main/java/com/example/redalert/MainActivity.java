package com.example.redalert;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // load the XML layout

        // 📚 View Health Articles
        Button btnViewArticles = findViewById(R.id.btnViewArticles);
        btnViewArticles.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, HealthArticlesActivity.class));
        });

        // 🔮 Start Cycle Prediction
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
    }
}
