package com.example.redalert;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Calendar;

public class ReminderActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "reminder_channel";
    private int selectedHour = 10;
    private int selectedMinute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        createNotificationChannel();

        Button notifyButton = findViewById(R.id.btnNotifyNow);
        notifyButton.setOnClickListener(v -> showReminderNotification());

        Button pickTimeButton = findViewById(R.id.btnPickTime);
        pickTimeButton.setOnClickListener(v -> showTimePicker());

        Button scheduleButton = findViewById(R.id.btnScheduleReminder);
        scheduleButton.setOnClickListener(v -> scheduleDailyReminder(selectedHour, selectedMinute));

        Button cancelButton = findViewById(R.id.btnCancelReminder);
        cancelButton.setOnClickListener(v -> cancelDailyReminder());
    }

    private void showReminderNotification() {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Red Alert Reminder")
                .setContentText("💡 Don't forget to check your wellness today!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(1, notification);
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (TimePicker view, int hourOfDay, int minute) -> {
            selectedHour = hourOfDay;
            selectedMinute = minute;
            Toast.makeText(this, "⏰ Time selected: " + selectedHour + ":" + String.format("%02d", selectedMinute), Toast.LENGTH_SHORT).show();
        }, selectedHour, selectedMinute, true);

        timePickerDialog.show();
    }

    private void scheduleDailyReminder(int hour, int minute) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        long triggerTime = calendar.getTimeInMillis();
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            triggerTime += AlarmManager.INTERVAL_DAY;
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "✅ Daily reminder set for " + hour + ":" + String.format("%02d", minute), Toast.LENGTH_LONG).show();
    }

    private void cancelDailyReminder() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(this, "❌ Daily reminder canceled", Toast.LENGTH_SHORT).show();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Reminders";
            String description = "Cycle and wellness reminders";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
