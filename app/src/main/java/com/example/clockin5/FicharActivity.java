package com.example.clockin5;

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

import com.example.clockin5.modelo.Jornada;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class FicharActivity extends AppCompatActivity {

    RadioButton rbEntrada, rbSalida;
    RadioGroup radioGroup;
    EditText etNotas, etContra;
    String ubicacion = "", notas = "";
    int tipo;

    ArrayList<Jornada> jornadaArrayList;

    Gestor g;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        /*Gestor g = new Gestor();
        jornadaArrayList = g.getJornadaArrayList();*/

        etNotas = findViewById(R.id.etNotas);
        etContra = findViewById(R.id.etPassFichar);

        rbEntrada = findViewById(R.id.rb_entrada);
        rbSalida = findViewById(R.id.rb_salida);

        radioGroup = findViewById(R.id.radioGroup);

        //Toast.makeText(FicharActivity.this, "" + jornadaArrayList.size(), Toast.LENGTH_LONG).show();

        FloatingActionButton fab = findViewById(R.id.fabFichar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioGroup.isSelected()) {
                    if (rbEntrada.isSelected()) {
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

                                Toast.makeText(FicharActivity.this, "" + (id+ 1), Toast.LENGTH_LONG).show();

                            /*Intent i = new Intent(FicharActivity.this, MainActivity.class);
                            startActivity(i);*/
                            } else {
                                Toast.makeText(FicharActivity.this, "Error 1", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(FicharActivity.this, "Error", Toast.LENGTH_LONG).show();
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
}
