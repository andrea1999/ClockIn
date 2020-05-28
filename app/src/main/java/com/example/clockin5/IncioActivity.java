package com.example.clockin5;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.*;
import com.applandeo.materialcalendarview.listeners.*;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class IncioActivity extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        Bundle bu = new Bundle();
        String b = bu.getString("names");

        /*Calendar calendar = Calendar.getInstance();

        calendarView.findViewById(R.id.calendarView);

        Calendar selectedDate = calendarView.getFirstSelectedDate();

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(@NotNull EventDay eventDay) {
                Calendar selectedDate = calendarView.getFirstSelectedDate();
                Toast.makeText(IncioActivity.this, selectedDate + "", Toast.LENGTH_SHORT).show();
            }
        });*/


    }
}