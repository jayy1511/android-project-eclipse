package com.example.redalert;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PredictionResultActivity extends AppCompatActivity {

    TextView nextPeriodText, moodText, healthTipText, fertilityText, dayOfCycleText, ovulationInfoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_result);

        // Link views
        nextPeriodText = findViewById(R.id.nextPeriodText);
        moodText = findViewById(R.id.moodText);
        healthTipText = findViewById(R.id.healthTipText);
        fertilityText = findViewById(R.id.fertilityText);
        dayOfCycleText = findViewById(R.id.dayOfCycleText);
        ovulationInfoText = findViewById(R.id.ovulationInfoText);

        // Get selected date from StartCycleActivity
        Intent intent = getIntent();
        int year = intent.getIntExtra("year", 0);
        int month = intent.getIntExtra("month", 0);
        int day = intent.getIntExtra("day", 0);

        // Setup Calendar with selected date
        Calendar startDate = Calendar.getInstance();
        startDate.set(year, month, day);

        // Predict next period (28 days later)
        Calendar nextPeriod = (Calendar) startDate.clone();
        nextPeriod.add(Calendar.DAY_OF_MONTH, 28);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        nextPeriodText.setText("📅 Next Period: " + sdf.format(nextPeriod.getTime()));

        // Calculate current day in cycle
        Calendar today = Calendar.getInstance();
        long diffMillis = today.getTimeInMillis() - startDate.getTimeInMillis();
        int dayOfCycle = (int) (diffMillis / (1000 * 60 * 60 * 24)) + 1;

        // Ovulation logic
        int ovulationDay = 14; // assuming 28-day cycle
        int fertileStart = ovulationDay - 3;
        int fertileEnd = ovulationDay + 2;

        String mood, healthTip, fertility;

        if (dayOfCycle < 5) {
            mood = "Tired / Low Energy";
            healthTip = "Stay hydrated and rest well.";
            fertility = "Low pregnancy chance";
        } else if (dayOfCycle < fertileStart) {
            mood = "Energetic";
            healthTip = "Ideal time for exercise and productivity.";
            fertility = "Medium pregnancy chance";
        } else if (dayOfCycle <= fertileEnd) {
            mood = "Confident / Peak Mood";
            healthTip = "Ovulation time – maintain good hygiene.";
            fertility = "High pregnancy chance 🌟";
        } else if (dayOfCycle <= 22) {
            mood = "Moody / Cravings";
            healthTip = "Maintain a stable sleep and food routine.";
            fertility = "Medium pregnancy chance";
        } else {
            mood = "Irritable / Fatigue";
            healthTip = "You may be approaching your period.";
            fertility = "Low pregnancy chance";
        }

        // Set predictions to UI with emojis
        moodText.setText("😊 Mood: " + mood);
        healthTipText.setText("💡 Tip: " + healthTip);
        fertilityText.setText("🍼 Fertility: " + fertility);

        // New: Day of Cycle and Ovulation Window
        dayOfCycleText.setText("📆 Day of Cycle: " + dayOfCycle);
        ovulationInfoText.setText("🧬 Ovulation Day: 14\n🩸 Fertile Window: Days " + fertileStart + "–" + fertileEnd);

        // View Tips button
        Button viewTipsButton = findViewById(R.id.viewTipsButton);
        String finalMood = mood;

        viewTipsButton.setOnClickListener(v -> {
            Intent tipsIntent = new Intent(PredictionResultActivity.this, TipsActivity.class);
            tipsIntent.putExtra("mood", finalMood);
            startActivity(tipsIntent);
        });
    }
}
