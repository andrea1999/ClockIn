package com.example.clockin5;

import android.content.Intent;
import android.os.AsyncTask;
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

import com.example.clockin5.modelo.Empleado;
import com.example.clockin5.ui.inicio.IncioFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static Connection conexionMySQL;
    TextView tvEmailDrawer, tvNombreDrawer;
    private AppBarConfiguration mAppBarConfiguration;

    private Connection con;

    private String user;

    private String baseDatos = "bvwxh4xvfrdqp7ztq9sz";
    private String usuario = "uddpnkeunuezlkmg";
    private String contrasena = "86F5ES4UEbzwUUxq52eJ";
    private String ip = "bvwxh4xvfrdqp7ztq9sz-mysql.services.clever-cloud.com";
    private String puerto = "3306";
    private String urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto + "/" + baseDatos;

    /*public static ArrayList<Empleado> getEmpleadoArrayList() {
        return empleadoArrayList;
    }*/

    //private static ArrayList<Empleado> empleadoArrayList;

    /*private static ArrayList<Empleado> empleadoArrayList = new ArrayList<Empleado>();
    private static ArrayList<Jornada> jornadaArrayList = new ArrayList<Jornada>();
    private static ArrayList<RegistroJornada> registroJornadasArrayList = new ArrayList<RegistroJornada>();
    private static ArrayList<RegistroEmpleados> registroEmpleadosArrayList = new ArrayList<RegistroEmpleados>();

    private Gestor g;*/

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Empleado e = null;

        //getJSON(urlConexionMySQL);

        //empleadoArrayList = new ArrayList<Empleado>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, FicharActivity.class);
                startActivity(i);
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new IncioFragment()).commit();

        /*Intent ii = new Intent(getApplicationContext(), IncioActivity.class);
        //ii.putExtra("names", "jakgl");
        startActivity(ii);*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

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

    /*private void getJSON(final String urlWebService) {
        class GetJSON extends AsyncTask<Void, Void, Void> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json).append("\n");
                    }
                } catch (Exception e) {

                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private class ConnectMySql extends AsyncTask<Void, Void, Void> {
        Empleado e = null;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(urlConexionMySQL, usuario, contrasena);
                System.out.println("Database section success");
                String result = "Database Connection Successful\n";
                e = buscarUsuario(con, e);
                Toast.makeText(MainActivity.this, "encontrado: " + e.getNombre(), Toast.LENGTH_SHORT)
                        .show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainActivity.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public Empleado buscarUsuario(Connection con, Empleado e) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from empleado");
        ResultSetMetaData rsmd = rs.getMetaData();
        while (rs.next()) {
            if (rs.getString(1).equals(user))
                e = new Empleado(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));
        }
        return e;
    }*/
}