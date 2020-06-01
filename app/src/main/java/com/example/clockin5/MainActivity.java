package com.example.clockin5;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.clockin5.modelo.Empleado;
import com.example.clockin5.ui.inicio.IncioFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    static Connection conexionMySQL;
    TextView tvEmailDrawer, tvNombreDrawer;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    String URL = "http://192.168.1.54/bd/consultartodosempleados.php";
    String mn;
    EmpleadosActivity.Adaptador adaptador;
    ArrayList<Empleado> empleadoArrayList = new ArrayList<Empleado>();
    String jsonString;
    JSONArray jsonArray;
    Empleado e;

    private AppBarConfiguration mAppBarConfiguration;
    private String user;
    private String baseDatos = "bvwxh4xvfrdqp7ztq9sz";
    private String usuario = "uddpnkeunuezlkmg";
    private String contrasena = "86F5ES4UEbzwUUxq52eJ";
    private String ip = "bvwxh4xvfrdqp7ztq9sz-mysql.services.clever-cloud.com";
    private String puerto = "3306";
    private String urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto + "/" + baseDatos;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        progressDialog = new ProgressDialog(this);

        getEmpleados();
        sharedPreferences = getSharedPreferences("SHARED_PREF_NAME", Context.MODE_PRIVATE);

        e = getUsuario();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FicharActivity.class);
                i.putExtra("ID", e.getIdEmp());
                startActivity(i);
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new IncioFragment()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (e.getJefe() == false) {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_empleados).setVisible(false);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_inicio) {
                    /*Intent ii = new Intent(getApplicationContext(), IncioActivity.class);
                    //ii.putExtra("names", "jakgl");
                    startActivity(ii);*/
                } else if (id == R.id.nav_cerrar) {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    Intent ic = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(ic);
                } else if (id == R.id.nav_empleados) {
                    Intent ie = new Intent(MainActivity.this, EmpleadosActivity.class);
                    startActivity(ie);
                } else if (id == R.id.nav_password) {
                    Intent i = new Intent(getApplicationContext(), PasswordActitvity.class);
                    //i.putExtra("names", "jakgl");
                    startActivity(i);
                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        View headerView = navigationView.getHeaderView(0);
        tvNombreDrawer = (TextView) headerView.findViewById(R.id.tvNombreDrawer);
        tvNombreDrawer.setText(e.getNombre());

        tvEmailDrawer = (TextView) headerView.findViewById(R.id.tvEmailDrawer);
        tvEmailDrawer.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void getEmpleados() {
        progressDialog.setMessage("Fetching data from the Server...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();

                        Toast.makeText(MainActivity.this, "Data Successfully Fetched", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject js = new JSONObject(response);

                            JSONArray jsonArray = js.getJSONArray("empleado");

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

    public Empleado getUsuario() {
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
                    String id = jsonObject2.getString("id_emp");
                    String nombre = jsonObject2.getString("nombre");
                    String apellido1 = jsonObject2.getString("apellido1");
                    String apellido2 = jsonObject2.getString("apellido2");
                    String dni = jsonObject2.getString("dni");
                    String imagen = jsonObject2.getString("imagen");
                    int jefe = jsonObject2.getInt("jefe");
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
                    return empleado;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}