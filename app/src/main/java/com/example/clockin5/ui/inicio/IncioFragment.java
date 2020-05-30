package com.example.clockin5.ui.inicio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.clockin5.IncioActivity;
import com.example.clockin5.R;
import com.example.clockin5.RegistrosActivity;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class IncioFragment extends Fragment {
    private static final String TEXT = "text";
    Button b;
    CalendarView cv;

    public static IncioFragment newInstance() {
        IncioFragment frag = new IncioFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_inicio, container, false);

        if (getArguments() != null) {
            ((TextView) layout.findViewById(R.id.text)).setText(getArguments().getString(TEXT));
        }

        cv = layout.findViewById(R.id.calendarView);
        cv.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(@NotNull EventDay eventDay) {
                Calendar selectedDate = cv.getFirstSelectedDate();
                Toast.makeText(getActivity(), ""+ selectedDate , Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), RegistrosActivity.class);
                startActivity(i);
            }
        });

        b = (Button) layout.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), RegistrosActivity.class);
                startActivity(i);
            }
        });




        return layout;
    }
}
