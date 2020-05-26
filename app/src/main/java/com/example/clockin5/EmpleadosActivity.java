package com.example.clockin5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clockin5.modelo.Empleado;
import com.example.clockin5.ui.empleados.EmpleadosFragment;

import java.util.ArrayList;

public class EmpleadosActivity extends AppCompatActivity {
    /*private ListView lista;
    Adaptador adaptador;

    ArrayList<Empleado> empleados;*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);

        /*empleados = new ArrayList<Empleado>();
        Empleado e1 = new Empleado(01, "Steve", "Rogers", "Capitán América", "fjlk", "tjgdfkll", false);
        Empleado e2 = new Empleado(02, "Tony", "Stark", "Ironman", "dgtgre", "rgfthytu", true);
        Empleado e3 = new Empleado(03, "Peter", "Parker", "Spiderman", "fdgtetgvf", "fvbghngu", false);

        empleados.add(e1);
        empleados.add(e2);
        empleados.add(e3);

        lista = findViewById(R.id.list);

        adaptador = new Adaptador(this, empleados);
        lista.setAdapter(adaptador);*/
    }

    /*class Adaptador extends ArrayAdapter<Empleado> {
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

            } else
                contenedor = (Contenedor) item.getTag();

            contenedor.nombre.setText(empleados.get(position).getNombre());
            contenedor.getEmail().setText(empleados.get(position).getIdEmp());

            return (item);
        }
    }*/

    /*static class Contenedor {
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
    }*/
}
