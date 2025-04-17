package com.example.redalert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

        // 🔐 Redirect to Login if not signed in
        if (user == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);
    }

    // ✅ Dashboard Card Click Handlers

    public void onViewHealthArticlesClicked(View view) {
        startActivity(new Intent(this, HealthArticlesActivity.class));
    }

    public void onPredictPeriodClicked(View view) {
        startActivity(new Intent(this, PredictionResultActivity.class));
    }

    public void onDailyQuoteClicked(View view) {
        startActivity(new Intent(this, DailyQuoteActivity.class));
    }

    public void onReminderClicked(View view) {
        startActivity(new Intent(this, ReminderActivity.class));
    }

    public void onUserProfileClicked(View view) {
        startActivity(new Intent(this, UserProfileActivity.class));
    }

    public void onCycleCalendarClicked(View view) {
        startActivity(new Intent(this, CycleCalendarActivity.class));
    }

    public void onMoodTrackerClicked(View view) {
        startActivity(new Intent(this, MoodSymptomActivity.class));
    }

    public void onMoodHistoryClicked(View view) {
        startActivity(new Intent(this, MoodSymptomHistoryActivity.class));
    }

    public void onTipsClicked(View view) {
        startActivity(new Intent(this, TipsActivity.class));
    }

    public void onLogoutClicked(View view) {
        auth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
