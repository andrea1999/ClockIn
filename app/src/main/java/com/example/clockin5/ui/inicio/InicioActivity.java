package com.example.clockin5.ui.inicio;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.clockin5.R;

public class InicioActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        Bundle bu = new Bundle();
        String b = bu.getString("names");

        /*tv = findViewById(R.id.textView3);
        tv.setText(b);*/
    }
}