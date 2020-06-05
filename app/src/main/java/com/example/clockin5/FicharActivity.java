package com.example.clockin5;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clockin5.modelo.Empleado;
import com.example.clockin5.modelo.Jornada;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FicharActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    RadioButton rbEntrada, rbSalida;
    RadioGroup radioGroup;
    EditText etNotas, etContra;
    String ubicacion = "", notas = "", hora = "", fecha = "", tipoS = "", idRJ;
    int tipo;
    String idE;
    Empleado e;
    ArrayList<Jornada> jornadaArrayList;
    ArrayList<Empleado> empleadoArrayList;
    RequestQueue rq;
    SharedPreferences sharedPreferences;
    String jsonString;
    JSONArray jsonArray;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichar);

        empleadoArrayList = new ArrayList<>();

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        idRJ = "";

        //obtenemos el id del usuario actual
        Bundle b = getIntent().getExtras();
        idE = b.getString("ID");

        rq = Volley.newRequestQueue(this);

        etNotas = findViewById(R.id.etNotas);
        etContra = findViewById(R.id.etPassFichar);

        rbEntrada = findViewById(R.id.rb_entrada);
        rbSalida = findViewById(R.id.rb_salida);

        radioGroup = findViewById(R.id.radioGroup);

        empleados();

        sharedPreferences = getSharedPreferences("SHARED_PREF_NAME", Context.MODE_PRIVATE);

        //si accede un jefe no necesita introducir la contraseña del empleado
        if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(idE)) {
            etContra.setEnabled(false);
            etContra.setHint("Sólo para rol Empleado");
        }

        //insertamos el registro
        FloatingActionButton fab = findViewById(R.id.fabFichar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond();
                fecha = LocalDateTime.now().getDayOfMonth() + "-" + LocalDateTime.now().getMonthValue() + "-" + LocalDateTime.now().getYear();
                e = null;

                //buscamos el id del empleado
                for (int i = 0; i < empleadoArrayList.size(); i++) {
                    String idE = empleadoArrayList.get(i).getIdEmp();
                    if (idE.equals(idE)) {
                        e = empleadoArrayList.get(i);
                    }
                }

                //si es el actual, realiza las comprobaciones de contraseña
                if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(idE)) {
                    if (etContra.getText().toString().isEmpty()) {
                        Toast.makeText(FicharActivity.this, "Introduce la contraseña", Toast.LENGTH_LONG).show();
                    } else {
                        if (rbSalida.isChecked() || rbEntrada.isChecked()) {
                            if (rbEntrada.isChecked()) {
                                tipo = 0;
                                tipoS = "Entrada";
                                notas = etNotas.getText().toString();
                                ubicacion = "Taller Focus S.L.";
                            } else {
                                tipo = 1;
                                tipoS = "Salida";
                                ubicacion = "";
                                notas = etNotas.getText().toString();
                            }

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            AuthCredential credential = EmailAuthProvider
                                    .getCredential(user.getEmail(), etContra.getText().toString());
                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(FicharActivity.this, "Contraseña correcta", Toast.LENGTH_LONG).show();

                                        registrarJornada();

                                        Intent i = new Intent(FicharActivity.this, MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(FicharActivity.this, "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(FicharActivity.this, "Selecciona si es entrada o salida", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    if (rbSalida.isChecked() || rbEntrada.isChecked()) {
                        if (rbEntrada.isChecked()) {
                            tipo = 0;
                            tipoS = "Entrada";
                            notas = etNotas.getText().toString();
                            ubicacion = "Taller Focus S.L.";
                        } else {
                            tipo = 1;
                            tipoS = "Salida";
                            ubicacion = "";
                            notas = etNotas.getText().toString();
                        }
                        Toast.makeText(FicharActivity.this, idRJ + " " + tipo + " " + notas + " " + ubicacion + " " + hora, Toast.LENGTH_LONG).show();

                        registrarJornada();

                        Intent i = new Intent(FicharActivity.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(FicharActivity.this, "Selecciona si es entrada o salida", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
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

    //buscamos la lista de empleados
    public void empleados() {
        SharedPreferences sp = getSharedPreferences("SHARED_PREF_NAME", Context.MODE_PRIVATE);
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
                e.getMessage();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    //metodo para registrar una jornada con php
    public void registrarJornada() {
        RequestQueue queue = Volley.newRequestQueue(FicharActivity.this);
        String URL = "http://192.168.1.54/bd/guardarjornada.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(FicharActivity.this, "success v", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FicharActivity.this, "error v", Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tipo", tipoS);
                params.put("ubicacion", ubicacion);
                params.put("hora", hora);
                params.put("nota", notas);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    //metodo para buscar el metodo de la nueva jornada
    public void getIdJ() {
        String URL1 = "http://192.168.1.54/bd/consultarjornadas.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL1,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject js = new JSONObject(response);

                            JSONArray jsonArray = js.getJSONArray("jornada");

                            jsonString = jsonArray.toString();

                            SharedPreferences.Editor editor = sharedPreferences.edit();

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

    //metodo para cargar el id de jornada
    public String getIdJornada() {
        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREF_NAME", Context.MODE_PRIVATE);
        jsonString = sharedPreferences.getString("jsonString", null);

        //here, string to jsonArray conversion takes place
        try {
            jsonArray = new JSONArray(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String idB = jsonObject.getString("id_emp");
                //simple if statement allows only those jsonObjects to be added the laptopList where price is less than 40000.
                if (idB.equals(FirebaseAuth.getInstance().getUid())) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                    String id = jsonObject2.getString("0");
                    return id;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //
    //metodo para registrar una jornada con php
    public void registrarRJornada() {
        RequestQueue queue = Volley.newRequestQueue(FicharActivity.this);
        String URL = "http://192.168.1.54/bd/guardarjornada.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(FicharActivity.this, "success v", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FicharActivity.this, "error v", Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_emp", idE);
                params.put("id_jornada", idRJ);
                params.put("fecha", fecha);
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
