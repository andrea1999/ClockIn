package com.example.clockin5;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clockin5.modelo.Empleado;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class EmpleadosActivity extends AppCompatActivity {
    private ListView lista;

    Adaptador adaptador;
    ArrayList<Empleado> empleados;

    static Connection conexionMySQL;

    private String baseDatos = "bvwxh4xvfrdqp7ztq9sz";
    private String usuario = "uddpnkeunuezlkmg";
    private String contrasena = "86F5ES4UEbzwUUxq52eJ";
    private String ip = "bvwxh4xvfrdqp7ztq9sz-mysql.services.clever-cloud.com";
    private String puerto = "3306";
    private String urlConexionMySQL = "jdbc:mysql://" + ip + ":" + puerto + "/" + baseDatos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        lista = findViewById(R.id.list);

        adaptador = new Adaptador(this, empleados);
        lista.setAdapter(adaptador);

        //getJSON("http://192.168.1.54/bd/consultartodosempleados.php");
        //getJSON("http://bvwxh4xvfrdqp7ztq9sz-mysql.services.clever-cloud.com/consultartodosempleados.php");

        /*final ConnectMySql connectMySql = new ConnectMySql();
        connectMySql.execute();*/

        //loadIntoListView(conexionMySQL);
    }

    /*private void getJSON(final String urlWebService) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                try {
                    loadIntoListView(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json).append("\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void loadIntoListView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        empleados = new ArrayList<Empleado>();

        String[] heroes = new String[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            heroes[i] = "" + obj.getString("id_emp") + "," +
                    obj.getString("nombre") + "," +
                    obj.getString("apellido1") + "," +
                    obj.getString("apellido2") + "," +
                    obj.getString("dni") + "," +
                    obj.getString("imagen") + "," +
                    obj.getBoolean("jefe");

            String[] heroe = heroes[i].split(",");
            empleados.add(new Empleado(heroe[0], heroe[1], heroe[2], heroe[3], heroe[4], heroe[5], Boolean.parseBoolean(heroe[6])));
        }

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, heroes);
        //lista.setAdapter(arrayAdapter);

        adaptador = new Adaptador(this, empleados);
        lista.setAdapter(adaptador);
    }*/

     class Adaptador extends ArrayAdapter<Empleado> {
        public Adaptador(Context context, ArrayList<Empleado> empleados) {
            super(context, R.layout.list_item_empleado, empleados);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View item = convertView;
            Contenedor contenedor;

            if (item == null) {
                LayoutInflater inflador = LayoutInflater.from(getContext());
                item = inflador.inflate(R.layout.list_item_empleado, null);

                contenedor = new Contenedor();
                contenedor.setNombre((TextView) item.findViewById(R.id.lblnombre));
                contenedor.setEmail((TextView) item.findViewById(R.id.lblemail));
                item.setTag(contenedor);

            } else {
                contenedor = (Contenedor) item.getTag();
        }
            String id = empleados.get(position).getIdEmp();

            contenedor.nombre.setText(empleados.get(position).getNombre());
            contenedor.getEmail().setText(empleados.get(position).getApellido1() + " " + empleados.get(position).getApellido2());

            return (item);
        }
    }

    static class Contenedor {
        TextView nombre;
        TextView email;

        public TextView getNombre() {
            return nombre;
        }

        public void setNombre(TextView nombre) {
            this.nombre = nombre;
        }

        public TextView getEmail() {
            return email;
        }

        public void setEmail(TextView email) {
            this.email = email;
        }
    }

    /*private class ConnectMySql extends AsyncTask<Void, Void, Empleado> {
        Empleado e = null;

        @Override
        protected Empleado doInBackground(Void... voids) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexionMySQL = DriverManager.getConnection(urlConexionMySQL, usuario, contrasena);
                System.out.println("Databaseection success");

                loadIntoListView(conexionMySQL);

                Toast.makeText(EmpleadosActivity.this, "encontrado: " + e.getNombre(), Toast.LENGTH_SHORT)
                        .show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return e;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(EmpleadosActivity.this, "Please wait...", Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        protected void onPostExecute(Empleado empleado) {
            super.onPostExecute(empleado);
        }
    }*/

    /*public Empleado buscarUsuario(Connection con, Empleado e) throws SQLException {
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from empleado");
        ResultSetMetaData rsmd = rs.getMetaData();

        while (rs.next()) {
            if (rs.getString(1).equals(user))
                e = new Empleado(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));
        }

        return e;
    }*/

    /*private void loadIntoListView(Connection con) {
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from empleado");
            ResultSetMetaData rsmd = rs.getMetaData();

            while (rs.next()) {
                Empleado e = new Empleado(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getBoolean(7));
                empleados.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        adaptador = new Adaptador(this, empleados);
        lista.setAdapter(adaptador);
    }*/

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
