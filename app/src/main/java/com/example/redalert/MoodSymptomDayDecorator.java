package com.example.redalert;

import android.graphics.Color;
import android.text.style.BackgroundColorSpan;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Set;

public class MoodSymptomDayDecorator implements DayViewDecorator {

    private final Set<CalendarDay> dates;

    public MoodSymptomDayDecorator(Set<CalendarDay> dates) {
        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new BackgroundColorSpan(Color.parseColor("#FFEB3B"))); // Yellow
    }
}
