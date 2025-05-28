package com.example.redalert;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.HashSet;
import java.util.Set;

public class CycleCalendarActivity extends AppCompatActivity {

    MaterialCalendarView calendarView;
    TextView selectedDateText;
    FirebaseFirestore db;
    FirebaseAuth auth;

    Set<CalendarDay> moodSymptomDays = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_calendar);

        calendarView = findViewById(R.id.calendarView);
        selectedDateText = findViewById(R.id.selectedDateText);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        loadMoodSymptomData();

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            String formatted = date.getYear() + "-" + String.format("%02d", date.getMonth() + 1) + "-" + String.format("%02d", date.getDay());
            selectedDateText.setText("📅 " + formatted);

            // 👉 Launch MoodSymptomActivity for editing/viewing that day
            Intent intent = new Intent(this, MoodSymptomActivity.class);
            intent.putExtra("selected_date", formatted);
            startActivity(intent);
        });
    }

    private void loadMoodSymptomData() {
        String uid = auth.getCurrentUser().getUid();
        db.collection("mood_symptoms")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        for (String key : documentSnapshot.getData().keySet()) {
                            String[] parts = key.split("-");
                            CalendarDay day = CalendarDay.from(
                                    Integer.parseInt(parts[0]),
                                    Integer.parseInt(parts[1]) - 1,
                                    Integer.parseInt(parts[2])
                            );
                            moodSymptomDays.add(day);
                        }
                        calendarView.addDecorator(new MoodSymptomDayDecorator(moodSymptomDays));
                    }
                });
    }
}
