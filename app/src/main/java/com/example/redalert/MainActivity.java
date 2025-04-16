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
            Intent intent = new Intent(MainActivity.this, HealthArticlesActivity.class);
            startActivity(intent);
        });

        // 🔮 Start Cycle Prediction
        Button btnStartCycle = findViewById(R.id.btnStartCycle);
        btnStartCycle.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, StartCycleActivity.class);
            startActivity(intent);
        });

        // 💬 Daily Quote
        Button btnDailyQuote = findViewById(R.id.btnDailyQuote);
        btnDailyQuote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DailyQuoteActivity.class);
            startActivity(intent);
        });
    }
}
