package com.example.redalert;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 🚨 THIS launches your custom screen instead of Hello World
        startActivity(new Intent(MainActivity.this, StartCycleActivity.class));
        finish(); // Optional: prevent back button from going back to this
    }
}
