package com.example.clockin5;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class RegistrosActivity extends AppCompatActivity {
    TextView tvFecha, tvEntrada, tvSalida, tvTotal, tvLocalizacion, tvNotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        tvFecha = findViewById(R.id.tvFecha);;

        Bundle b = this.getIntent().getExtras();
        String fecha = b.getString("fecha");
        tvFecha.setText(fecha);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.i("ActionBar", "Atrás!");
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
