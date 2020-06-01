package com.example.clockin5;

import android.content.Context;
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

public class FicharActivity extends AppCompatActivity {

    RadioButton rbEntrada, rbSalida;
    RadioGroup radioGroup;
    EditText etNotas, etContra;
    String ubicacion = "", notas = "";
    int tipo;
    String id;

    ArrayList<Jornada> jornadaArrayList;
    ArrayList<Empleado> empleadoArrayList;

    String URL = "http://192.168.1.54/bd/consultartodosempleados.php";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichar);

        empleadoArrayList = new ArrayList<>();

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Bundle b = getIntent().getExtras();
        id = b.getString("ID");

        Toast.makeText(FicharActivity.this, "" + id, Toast.LENGTH_LONG).show();

        etNotas = findViewById(R.id.etNotas);
        etContra = findViewById(R.id.etPassFichar);

        rbEntrada = findViewById(R.id.rb_entrada);
        rbSalida = findViewById(R.id.rb_salida);

        radioGroup = findViewById(R.id.radioGroup);

        empleados();

        Toast.makeText(FicharActivity.this, "" + empleadoArrayList.get(0).getDni(), Toast.LENGTH_LONG).show();

        if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(id)) {
            etContra.setEnabled(false);
        }

        FloatingActionButton fab = findViewById(R.id.fabFichar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FirebaseAuth.getInstance().getCurrentUser().getUid().equals(id)) {
                    if (etContra.getText().toString().isEmpty()) {

                    } else {
                        if (rbSalida.isChecked() || rbEntrada.isChecked()) {
                            if (rbEntrada.isChecked()) {
                                tipo = 0;
                                ubicacion = "aaa";
                                notas = etNotas.getText().toString();
                            } else {
                                tipo = 1;
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
                                        int id = 0; //jornadaArrayList.size();
                        /*Jornada j = new Jornada(id, tipo, ubicacion, Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()), notas);
                        jornadaArrayList.add(j);*/

                                        Toast.makeText(FicharActivity.this, id + " " + tipo + " " + notas + " " + ubicacion + " " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond(), Toast.LENGTH_LONG).show();

                                        Toast.makeText(FicharActivity.this, "Contraseña correcta", Toast.LENGTH_LONG).show();

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
                            ubicacion = "aaa";
                            notas = etNotas.getText().toString();
                        } else {
                            tipo = 1;
                            ubicacion = "";
                            notas = etNotas.getText().toString();
                        }
                        Toast.makeText(FicharActivity.this, id + " " + tipo + " " + notas + " " + ubicacion + " " + LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute() + ":" + LocalDateTime.now().getSecond(), Toast.LENGTH_LONG).show();
                        //
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
}
