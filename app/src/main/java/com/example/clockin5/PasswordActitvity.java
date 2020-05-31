package com.example.clockin5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordActitvity extends AppCompatActivity {

    EditText etActual, etNueva, etConfirmar;
    Button bPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        etActual = findViewById(R.id.etActual);
        etNueva = findViewById(R.id.etNueva);
        etConfirmar = findViewById(R.id.etConfirmar);

        bPass = findViewById(R.id.botonPass);

        bPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String actual, nueva, confirmar;
                actual = etActual.getText().toString();
                nueva = etNueva.getText().toString();
                confirmar = etConfirmar.getText().toString();
                if (actual.equals("") || nueva.equals("") || confirmar.equals("")) {
                    Toast.makeText(PasswordActitvity.this, "No puede haber ningún campo vacío", Toast.LENGTH_SHORT).show();
                } else if (nueva.length() < 6 && confirmar.length() < 6) {
                    Toast.makeText(PasswordActitvity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                } else if (actual.equals(nueva)) {
                    Toast.makeText(PasswordActitvity.this, "La contraseña nueva debe ser diferente a la actual", Toast.LENGTH_SHORT).show();
                } else if (!nueva.equals(confirmar)) {
                    Toast.makeText(PasswordActitvity.this, "La contraseña nueva no coincide con la confirmacion", Toast.LENGTH_SHORT).show();
                } else {
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), actual);
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(nueva).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(PasswordActitvity.this, "Contraseña cambiada correctamente", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(PasswordActitvity.this, MainActivity.class);
                                            startActivity(i);
                                        } else {
                                            Toast.makeText(PasswordActitvity.this, "No se ha podido cambiar la contraseña", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(PasswordActitvity.this, "No se ha podido cambiar la contraseña", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
