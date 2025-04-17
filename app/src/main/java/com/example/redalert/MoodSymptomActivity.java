package com.example.redalert;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MoodSymptomActivity extends AppCompatActivity {

    private Spinner moodSpinner, symptomSpinner;
    private Button btnSave;

    private final String[] moods = {"Happy", "Sad", "Irritable", "Tired", "Energetic"};
    private final String[] symptoms = {"Cramps", "Headache", "Bloating", "Acne", "None"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood_symptom);

        moodSpinner = findViewById(R.id.moodSpinner);
        symptomSpinner = findViewById(R.id.symptomSpinner);
        btnSave = findViewById(R.id.btnSaveMoodSymptom);

        moodSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, moods));
        symptomSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, symptoms));

        btnSave.setOnClickListener(v -> saveMoodAndSymptom());
    }

    private void saveMoodAndSymptom() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        String mood = moodSpinner.getSelectedItem().toString();
        String symptom = symptomSpinner.getSelectedItem().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("mood", mood);
        data.put("symptom", symptom);
        data.put("timestamp", System.currentTimeMillis());

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(user.getUid())
                .collection("mood_symptoms")
                .add(data)
                .addOnSuccessListener(docRef -> Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
