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

        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        // 📚 Health Articles
        findViewById(R.id.btnViewArticles).setOnClickListener(v ->
                startActivity(new Intent(this, HealthArticlesActivity.class)));

        // 🔮 Period Prediction
        findViewById(R.id.btnStartCycle).setOnClickListener(v ->
                startActivity(new Intent(this, StartCycleActivity.class)));

        // 💬 Daily Quote
        findViewById(R.id.btnDailyQuote).setOnClickListener(v ->
                startActivity(new Intent(this, DailyQuoteActivity.class)));

        // 🔔 Reminder
        findViewById(R.id.btnReminder).setOnClickListener(v ->
                startActivity(new Intent(this, ReminderActivity.class)));

        // 👤 Profile
        findViewById(R.id.btnUserProfile).setOnClickListener(v ->
                startActivity(new Intent(this, UserProfileActivity.class)));

        // 🚪 Logout
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // 🗓️ Calendar
        findViewById(R.id.btnCalendar).setOnClickListener(v ->
                startActivity(new Intent(this, CycleCalendarActivity.class)));

        // 🧠 Mood & Symptom Tracker
        findViewById(R.id.btnMoodSymptom).setOnClickListener(v ->
                startActivity(new Intent(this, MoodSymptomActivity.class)));

        // 🧘 Mood History
        findViewById(R.id.btnHistory).setOnClickListener(v ->
                startActivity(new Intent(this, MoodSymptomHistoryActivity.class)));
    }
}
