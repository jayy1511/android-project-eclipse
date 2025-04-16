package com.example.redalert;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PredictionResultActivity extends AppCompatActivity {

    TextView nextPeriodText, moodText, healthTipText, fertilityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction_result);

        // Link the TextViews with their IDs in XML
        nextPeriodText = findViewById(R.id.nextPeriodText);
        moodText = findViewById(R.id.moodText);
        healthTipText = findViewById(R.id.healthTipText);
        fertilityText = findViewById(R.id.fertilityText);

        // Receive the selected date from StartCycleActivity
        Intent intent = getIntent();
        int year = intent.getIntExtra("year", 0);
        int month = intent.getIntExtra("month", 0);
        int day = intent.getIntExtra("day", 0);

        // Create a Calendar instance with the selected date
        Calendar startDate = Calendar.getInstance();
        startDate.set(year, month, day);

        // Estimate next period (28-day cycle)
        Calendar nextPeriod = (Calendar) startDate.clone();
        nextPeriod.add(Calendar.DAY_OF_MONTH, 28);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        nextPeriodText.setText("Next Period: " + sdf.format(nextPeriod.getTime()));

        // Calculate current day of the cycle
        Calendar today = Calendar.getInstance();
        long diffMillis = today.getTimeInMillis() - startDate.getTimeInMillis();
        int dayOfCycle = (int) (diffMillis / (1000 * 60 * 60 * 24)) + 1;

        String mood, healthTip, fertility;

        // Basic prediction logic based on the cycle day
        if (dayOfCycle < 5) {
            mood = "Tired / Low Energy";
            healthTip = "Stay hydrated and rest well.";
            fertility = "Low pregnancy chance";
        } else if (dayOfCycle < 14) {
            mood = "Energetic";
            healthTip = "Ideal time for exercise and productivity.";
            fertility = "Medium pregnancy chance";
        } else if (dayOfCycle <= 16) {
            mood = "Confident / Peak Mood";
            healthTip = "Ovulation time – maintain good hygiene.";
            fertility = "High pregnancy chance";
        } else if (dayOfCycle <= 22) {
            mood = "Moody / Cravings";
            healthTip = "Maintain a stable sleep and food routine.";
            fertility = "Medium pregnancy chance";
        } else {
            mood = "Irritable / Fatigue";
            healthTip = "You may be approaching your period.";
            fertility = "Low pregnancy chance";
        }

        // Display results
        moodText.setText("Mood: " + mood);
        healthTipText.setText("Health Tip: " + healthTip);
        fertilityText.setText("Fertility: " + fertility);
    }
}
