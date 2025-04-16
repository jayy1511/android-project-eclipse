package com.example.redalert;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PredictionResultActivity extends AppCompatActivity {

    TextView nextPeriodText, moodText, healthTipText, fertilityText, dayOfCycleText, ovulationInfoText, quoteText;

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
        quoteText = findViewById(R.id.quoteText);

        // Get selected date from StartCycleActivity
        Intent intent = getIntent();
        int year = intent.getIntExtra("year", 0);
        int month = intent.getIntExtra("month", 0);
        int day = intent.getIntExtra("day", 0);

        Calendar startDate = Calendar.getInstance();
        startDate.set(year, month, day);

        Calendar nextPeriod = (Calendar) startDate.clone();
        nextPeriod.add(Calendar.DAY_OF_MONTH, 28);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        nextPeriodText.setText("\uD83D\uDCC5 Next Period: " + sdf.format(nextPeriod.getTime()));

        Calendar today = Calendar.getInstance();
        long diffMillis = today.getTimeInMillis() - startDate.getTimeInMillis();
        int dayOfCycle = (int) (diffMillis / (1000 * 60 * 60 * 24)) + 1;

        int ovulationDay = 14;
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
            fertility = "High pregnancy chance \uD83C\uDF1F";
        } else if (dayOfCycle <= 22) {
            mood = "Moody / Cravings";
            healthTip = "Maintain a stable sleep and food routine.";
            fertility = "Medium pregnancy chance";
        } else {
            mood = "Irritable / Fatigue";
            healthTip = "You may be approaching your period.";
            fertility = "Low pregnancy chance";
        }

        moodText.setText("\uD83D\uDE0A Mood: " + mood);
        healthTipText.setText("\uD83D\uDCA1 Tip: " + healthTip);
        fertilityText.setText("\uD83C\uDF7D Fertility: " + fertility);

        dayOfCycleText.setText("\uD83D\uDCC6 Day of Cycle: " + dayOfCycle);
        ovulationInfoText.setText("\uD83E\uDDEC Ovulation Day: 14\n\uD83E\uDDE8 Fertile Window: Days " + fertileStart + "–" + fertileEnd);

        Button viewTipsButton = findViewById(R.id.viewTipsButton);
        String finalMood = mood;
        viewTipsButton.setOnClickListener(v -> {
            Intent tipsIntent = new Intent(PredictionResultActivity.this, TipsActivity.class);
            tipsIntent.putExtra("mood", finalMood);
            startActivity(tipsIntent);
        });

        // 🔮 Load quote
        new QuoteTask().execute();
    }

    private class QuoteTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://api.quotable.io/random");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                JSONObject json = new JSONObject(response.toString());
                return "\u2728 \"" + json.getString("content") + "\"\n– " + json.getString("author");

            } catch (Exception e) {
                return "Could not load quote.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            quoteText.setText(result);
        }
    }
}