package com.example.clockin5;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.clockin5.modelo.Empleado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RegistrosActivity extends AppCompatActivity {
    TextView tvFecha, tvEntrada, tvSalida, tvTotal, tvLocalizacion, tvNotas;

    /*SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    String URL = "http://192.168.1.54/bd/consultarempleados.php";
    String mn;
    EmpleadosActivity.Adaptador adaptador;
    ArrayList<Empleado> empleadoArrayList = new ArrayList<Empleado>();
    String jsonString;
    String idF = null;*/

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

        /*SharedPreferences sp = getSharedPreferences("SHARED_PREF_NAME", Context.MODE_PRIVATE);
        String jsonString = sp.getString("jsonString", null);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id_emp");
                String nombre = jsonObject.getString("nombre");
                String apellido1 = jsonObject.getString("apellido1");
                String apellido2 = jsonObject.getString("apellido2");
                String dni = jsonObject.getString("dni");
                String imagen = jsonObject.getString("imagen");
                int jefe = jsonObject.getInt("jefe");
                if (imagen.isEmpty()) {
                    imagen = "";
                } else if (apellido2.isEmpty()) {
                    apellido2 = "";
                }
                boolean jefeB = false;
                if (jefe == 1) {
                    jefeB = true;
                }
                Empleado empleado = new Empleado(id, nombre, apellido1, apellido2, dni, imagen, jefeB);
                empleadoArrayList.add(empleado);
            } catch (Exception e) {

            }
        }*/
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
