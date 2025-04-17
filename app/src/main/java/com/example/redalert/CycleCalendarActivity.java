package com.example.redalert;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class CycleCalendarActivity extends AppCompatActivity {

    MaterialCalendarView calendarView;
    TextView selectedDateText;

    FirebaseFirestore db;
    FirebaseUser user;

    Set<CalendarDay> savedPeriodDays = new HashSet<>();
    int periodDuration = 4; // Default duration (can be dynamic later)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_calendar);

        calendarView = findViewById(R.id.calendarView);
        selectedDateText = findViewById(R.id.selectedDateText);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            String formatted = "📅 Selected: " + date.getDay() + "/" + date.getMonth() + "/" + date.getYear();
            selectedDateText.setText(formatted);

            // Save to Firestore
            savePeriodToFirestore(date.getDate());
        });

        loadSavedPeriodData(); // 🔄 Load user's previous periods
    }

    private void savePeriodToFirestore(Date startDate) {
        if (user == null) return;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        Set<CalendarDay> newPeriodDays = new HashSet<>();
        for (int i = 0; i < periodDuration; i++) {
            CalendarDay day = CalendarDay.from(calendar);
            newPeriodDays.add(day);

            String formatted = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());

            db.collection("users")
                    .document(user.getUid())
                    .collection("periods")
                    .document(formatted)
                    .set(new PeriodEntry(formatted)); // Save each day

            calendar.add(Calendar.DAY_OF_MONTH, 1); // Go to next day
        }

        savedPeriodDays.addAll(newPeriodDays);
        calendarView.addDecorator(new PeriodDayDecorator(savedPeriodDays));
        Toast.makeText(this, "🩸 Period saved!", Toast.LENGTH_SHORT).show();
    }

    private void loadSavedPeriodData() {
        if (user == null) return;

        db.collection("users")
                .document(user.getUid())
                .collection("periods")
                .get()
                .addOnSuccessListener(query -> {
                    for (var doc : query.getDocuments()) {
                        String dateStr = doc.getId();
                        try {
                            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateStr);
                            CalendarDay day = CalendarDay.from(date);
                            savedPeriodDays.add(day);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    calendarView.addDecorator(new PeriodDayDecorator(savedPeriodDays));
                })
                .addOnFailureListener(e -> Toast.makeText(this, "❌ Failed to load periods", Toast.LENGTH_SHORT).show());
    }

    // Helper class for Firestore
    public static class PeriodEntry {
        public String date;

        public PeriodEntry() {} // Needed for Firestore
        public PeriodEntry(String date) {
            this.date = date;
        }
    }
}
