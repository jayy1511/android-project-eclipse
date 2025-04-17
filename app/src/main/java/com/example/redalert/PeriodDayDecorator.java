package com.example.redalert;

import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import java.util.HashSet;
import java.util.Set;

public class PeriodDayDecorator implements DayViewDecorator {

    private final HashSet<CalendarDay> periodDays;

    public PeriodDayDecorator(Set<CalendarDay> days) {
        this.periodDays = new HashSet<>(days);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return periodDays.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFCDD2"))); // light red
    }
}
