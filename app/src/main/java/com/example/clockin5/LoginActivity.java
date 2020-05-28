package com.example.clockin5;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText txtEmail;
    EditText txtPassword;
    Button btnLogin;
    TextView contra;


    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.etNueva);
        txtPassword = findViewById(R.id.etConfirmar);
        btnLogin = findViewById(R.id.botonPass);
        contra = findViewById(R.id.tvContra);

        if (mAuth.getCurrentUser() == null){
            progressDialog = new ProgressDialog(this);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    signIn();
                }
            });

            contra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recuperarPasswordDiaglog();
                }
            });
        } else {
            Intent i = new Intent(getApplication(), MainActivity.class);
            startActivity(i);
        }



        //Toast.makeText(getApplicationContext(), ""+ mAuth.getCurrentUser(), Toast.LENGTH_SHORT).show();
    }

    private void recuperarPasswordDiaglog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recuperar Contraseña");

        LinearLayout linearLayout = new LinearLayout(this);
        final EditText emailEt = new EditText(this);
        emailEt.setHint("Email");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        emailEt.setMinEms(10);
        linearLayout.addView(emailEt);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        builder.setPositiveButton("Recuperar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email = emailEt.getText().toString().trim();
                beginRecovery(email);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.create().show();
    }

    private void beginRecovery(String email) {
        progressDialog.setMessage("Enviando email...");
        progressDialog.show();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Email enviado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error al enviar el email", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience

        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);

        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }*/

    private void signIn() {
        final String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (email.equals("")) {
            Toast.makeText(this, "El campo EMAIL no puede estar vacío", Toast.LENGTH_LONG).show();
        } else if (password.equals("")) {
            Toast.makeText(this, "El campo CONTRASEÑA no puede estar vacío", Toast.LENGTH_LONG).show();
        } else if (password.length() < 6) {
            Toast.makeText(LoginActivity.this , "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Iniciando sesión...");
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(getApplication(), MainActivity.class);
                                //i.putExtra(MainActivity.user, email);
                                startActivity(i);

                                Toast.makeText(LoginActivity.this, "Bienvenido: " + email, Toast.LENGTH_LONG).show();
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "No se pudo iniciar sesión", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        }



    }

}
