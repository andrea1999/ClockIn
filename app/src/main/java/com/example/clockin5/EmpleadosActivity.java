package com.example.clockin5;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clockin5.modelo.Empleado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class EmpleadosActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    Adaptador adaptador;

    ArrayList<Empleado> empleadoArrayList = new ArrayList<Empleado>();
    String idF = null;
    private ListView lista;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);

        lista = findViewById(R.id.list);

        //obtenemos los datos guardados en preferencias
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

        //una vez que los tengamos en un arraylist, los cargamos en una lista
        adaptador = new Adaptador(this, empleadoArrayList);
        lista.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(EmpleadosActivity.this, FicharActivity.class);
                intent.putExtra("ID", empleadoArrayList.get(i).getIdEmp());
                startActivity(intent);
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

    static class Contenedor {
        TextView nombre;
        TextView email;
        String id;

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

    //adaptador de listview con el formato de contenedor
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

            idF = empleadoArrayList.get(position).getIdEmp();

            contenedor.nombre.setText(empleadoArrayList.get(position).getNombre() + " " + empleadoArrayList.get(position).getApellido1());
            if (empleadoArrayList.get(position).getJefe() == true) {
                contenedor.getEmail().setText("Jefe");
            } else {
                contenedor.getEmail().setText("Empleado");
            }

            return (item);
        }
    }


}
