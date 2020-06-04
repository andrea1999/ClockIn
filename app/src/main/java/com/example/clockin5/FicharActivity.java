package com.example.clockin5;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clockin5.modelo.Empleado;
import com.example.clockin5.modelo.Jornada;
import com.example.clockin5.modelo.RegistroJornada;
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

public class FicharActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    RadioButton rbEntrada, rbSalida;
    RadioGroup radioGroup;
    EditText etNotas, etContra;
    String ubicacion = "", notas = "", hora = "", fecha = "", tipoS = "";
    int tipo, idJ;
    String idE;
    Empleado e;

    ArrayList<Jornada> jornadaArrayList;
    ArrayList<Empleado> empleadoArrayList;

    //String URL = "http://clockin.byethost32.com/consultartodosempleados.php";
    //String URL1 = "http://192.168.1.54/bd/consultarjornadas.php";
    RequestQueue rq;
    JsonRequest jrq;
    StringRequest sr;
    String jsonString;
    SharedPreferences sharedPreferences;
    /*Se declara una variable de tipo LocationManager encargada de proporcionar acceso al servicio de localización del sistema.*/
    private LocationManager locManager;
    /*Se declara una variable de tipo Location que accederá a la última posición conocida proporcionada por el proveedor.*/
    private Location loc;
    private double latitud, longitud, altura, precision;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichar);

        empleadoArrayList = new ArrayList<>();

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        latitud = 0;
        longitud = 0;
        altura = 0;
        precision = 0;

        idJ = 0;

        Bundle b = getIntent().getExtras();
        idE = b.getString("ID");

        rq = Volley.newRequestQueue(this);

        //Toast.makeText(FicharActivity.this, "" + id, Toast.LENGTH_LONG).show();

        etNotas = findViewById(R.id.etNotas);
        etContra = findViewById(R.id.etPassFichar);

        rbEntrada = findViewById(R.id.rb_entrada);
        rbSalida = findViewById(R.id.rb_salida);

        radioGroup = findViewById(R.id.radioGroup);

        empleados();

        //getIdJ();

        sharedPreferences = getSharedPreferences("SHARED_PREF_NAME", Context.MODE_PRIVATE);

        Toast.makeText(FicharActivity.this, "" + idJ, Toast.LENGTH_LONG).show();

        if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(idE)) {
            etContra.setEnabled(false);
            etContra.setHint("Sólo para rol Empleado");
        }


        Toast.makeText(FicharActivity.this, fecha, Toast.LENGTH_LONG).show();

        FloatingActionButton fab = findViewById(R.id.fabFichar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hora = LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond();
                fecha = LocalDateTime.now().getDayOfMonth() + "-" + LocalDateTime.now().getMonthValue() + "-" + LocalDateTime.now().getYear();
                e = null;
                for (int i = 0; i < empleadoArrayList.size(); i++) {
                    String idE = empleadoArrayList.get(i).getIdEmp();
                    if (idE.equals(idE)) {
                        e = empleadoArrayList.get(i);
                    }
                }

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
                                //obtenerUbicacion();
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
                                        //jornadaArrayList.size();
                                        Jornada j = new Jornada(idJ, tipo, ubicacion, hora, notas);
                                        RegistroJornada rj = new RegistroJornada(fecha, e, j);

                                        Toast.makeText(FicharActivity.this, idJ + " " + tipo + " " + notas + " " + ubicacion + " " + hora, Toast.LENGTH_LONG).show();

                                        Toast.makeText(FicharActivity.this, "Contraseña correcta", Toast.LENGTH_LONG).show();

                                        //registrar(tipoS, ubicacion, hora, notas);

                                        /*Intent i = new Intent(FicharActivity.this, MainActivity.class);
                                        startActivity(i);*/
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
                            //obtenerUbicacion();
                        } else {
                            tipo = 1;
                            tipoS = "Salida";
                            ubicacion = "";
                            notas = etNotas.getText().toString();
                        }

                        Jornada j = new Jornada(idJ, tipo, ubicacion, hora, notas);
                        RegistroJornada rj = new RegistroJornada(fecha, e, j);

                        Toast.makeText(FicharActivity.this, idJ + " " + tipo + " " + notas + " " + ubicacion + " " + hora, Toast.LENGTH_LONG).show();

                        //registrar(tipoS, ubicacion, hora, notas);

                                        /*Intent i = new Intent(FicharActivity.this, MainActivity.class);
                                        startActivity(i);*/

                    } else {
                        Toast.makeText(FicharActivity.this, "Selecciona si es entrada o salida", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });
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

            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }

    /*public String obtenerUbicacion(){
        ActivityCompat.requestPermissions(FicharActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ubicacion = "";
            Toast.makeText(FicharActivity.this, "No se han aceptado los permisios necesarios", Toast.LENGTH_LONG);
        } else {
            //Se asigna a la clase LocationManager el servicio a nivel de sistema a partir del nombre.
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            latitud = loc.getLatitude();
            longitud = loc.getLongitude();
            altura = loc.getAltitude();
            precision = loc.getAccuracy();

            ubicacion = "(" + latitud + ", " + longitud + ")";
        }
        return ubicacion;
    }*/

    /*public void registrar(final String tipoSS, final String ubicacion, final String hora, final String nota) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://192.168.1.54/bd/guardarjornada.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Jornada jornada = new Jornada();
                        try {
                            JSONObject objResultado = new JSONObject(response);
                            String extadox = objResultado.get("estado").toString();
                            if (!extadox.equalsIgnoreCase("exito")) {
                                Toast.makeText(FicharActivity.this, "error", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(FicharActivity.this, "acierto", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tipo", tipoS);
                params.put("ubicacion", ubicacion);
                params.put("hora", hora);
                params.put("nota", nota);
                return params;
            }
        };
        queue.add(stringRequest);
    }*/

    /*public void getIdJ() {
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
                            idJ=jsonValues.size();
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
    }*/

}
