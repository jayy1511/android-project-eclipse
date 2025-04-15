package com.example.redalert;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class StartCycleActivity extends AppCompatActivity {

    private TextView selectedDateText;
    private Button pickDateButton, predictButton;
    private int selectedYear, selectedMonth, selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_cycle);

        selectedDateText = findViewById(R.id.selectedDateText);
        pickDateButton = findViewById(R.id.pickDateButton);
        predictButton = findViewById(R.id.predictButton);

        pickDateButton.setOnClickListener(view -> showDatePicker());

        predictButton.setOnClickListener(view -> {
            Intent intent = new Intent(StartCycleActivity.this, PredictionResultActivity.class);
            intent.putExtra("year", selectedYear);
            intent.putExtra("month", selectedMonth);
            intent.putExtra("day", selectedDay);
            startActivity(intent);
        });
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    selectedYear = year;
                    selectedMonth = month;
                    selectedDay = dayOfMonth;
                    selectedDateText.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
