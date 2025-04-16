package com.example.redalert;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TipsActivity extends AppCompatActivity {

    TextView tipTextView1, tipTextView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        tipTextView1 = findViewById(R.id.tipText1);
        tipTextView2 = findViewById(R.id.tipText2);

        String mood = getIntent().getStringExtra("mood");

        if (mood == null) {
            tipTextView1.setText("❗ Mood not available.");
            return;
        }

        switch (mood) {
            case "Tired / Low Energy":
                tipTextView1.setText("💧 Stay hydrated and take small naps.");
                tipTextView2.setText("🍵 Warm tea and comfort food can help.");
                break;

            case "Energetic":
                tipTextView1.setText("🏃 Go for a light jog or dance workout.");
                tipTextView2.setText("🥗 Eat clean to support your energy.");
                break;

            case "Confident / Peak Mood":
                tipTextView1.setText("💪 Crush your goals today. You're glowing!");
                tipTextView2.setText("📅 Plan important tasks around this energy.");
                break;

            case "Moody / Cravings":
                tipTextView1.setText("🍫 Don’t feel guilty — enjoy in moderation.");
                tipTextView2.setText("🧘 Deep breaths, light walks, and journaling help.");
                break;

            case "Irritable / Fatigue":
                tipTextView1.setText("🛌 Rest your mind and body.");
                tipTextView2.setText("🎧 Listen to calming music or take a bath.");
                break;

            default:
                tipTextView1.setText("🌸 Stay positive. You're doing amazing!");
                tipTextView2.setText("🧘 Take it easy and treat yourself gently.");
                break;
        }
    }
}
