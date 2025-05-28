package com.example.redalert;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MoodSymptomHistoryActivity extends AppCompatActivity {

    TextView historyText;
    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyText = findViewById(R.id.historyText);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        String uid = auth.getCurrentUser().getUid();

        db.collection("mood_symptoms")
                .document(uid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    StringBuilder sb = new StringBuilder("📖 Mood & Symptom History:\n\n");
                    if (snapshot.exists()) {
                        for (String date : snapshot.getData().keySet()) {
                            sb.append("📅 ").append(date)
                                    .append("\n    ➤ ").append(snapshot.getString(date))
                                    .append("\n\n");
                        }
                        historyText.setText(sb.toString());
                    } else {
                        historyText.setText("No history found.");
                    }
                });
    }
}
