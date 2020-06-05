package com.example.clockin5;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clockin5.modelo.Jornada;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RegistrosActivity extends AppCompatActivity {
    TextView tvFecha, tvEntrada, tvSalida, tvTotal, tvLocalizacion, tvNotas;
    String jsonString;
    JSONArray jsonArray;
    SharedPreferences shared;
    ArrayList<Jornada> jornadas;
    String fecha;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        tvFecha = findViewById(R.id.tvFecha);
        tvNotas = findViewById(R.id.tvNotas);
        tvLocalizacion = findViewById(R.id.tvUbicacion);
        tvSalida = findViewById(R.id.tvSalida);
        tvEntrada = findViewById(R.id.tvEntrada);
        tvTotal = findViewById(R.id.tvTotal);

        Bundle b = this.getIntent().getExtras();
        fecha = b.getString("fecha");
        tvFecha.setText(fecha);

        user = FirebaseAuth.getInstance().getUid();

        //obtenemos el registro correspondiente al dia seleccionado
        getRegistros();
        shared = getSharedPreferences("registros", Context.MODE_PRIVATE);

        getDiario();
    }

    //vuelta a la pantalla de inicio
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

    //obtenemos los registros de la base de datos
    public void getRegistros() {
        String URL = "http://192.168.1.54/bd/consultarregistrosjornada.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject js = new JSONObject(response);

                            JSONArray jsonArray = js.getJSONArray("registro");

                            jsonString = jsonArray.toString();

                            SharedPreferences.Editor editor = shared.edit();

                            editor.putString("jsonString", jsonString);
                            editor.apply();

                            List<JSONObject> jsonValues = new ArrayList<JSONObject>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonValues.add(jsonArray.getJSONObject(i));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue request = Volley.newRequestQueue(this);
        request.add(stringRequest);
    }

    //seleccionamos aquellos que correspondan al dia seleccionado
    public void getDiario() {
        SharedPreferences sharedPreferences = getSharedPreferences("registros", Context.MODE_PRIVATE);
        jsonString = sharedPreferences.getString("jsonString", null);

        //here, string to jsonArray conversion takes place
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String empleado, tipo, ubicacion, hora, notas;
        int jornada;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fechar = jsonObject.getString("1");

                if (fechar.equals(fecha)) {
                    empleado = jsonObject.getString("0");
                    jornada = jsonObject.getInt("2");
                    tipo = jsonObject.getString("3");
                    ubicacion = jsonObject.getString("4");
                    hora = jsonObject.getString("5");
                    notas = jsonObject.getString("6");

                    //mostramos los datos en su correspondiente textview
                    if (user.equals(empleado)) {
                        if (tipo.equals("1")) {
                            tvSalida.setText(hora);
                        } else {
                            tvEntrada.setText(hora);
                            tvLocalizacion.setText(ubicacion);
                            tvNotas.setText(notas);
                        }

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
