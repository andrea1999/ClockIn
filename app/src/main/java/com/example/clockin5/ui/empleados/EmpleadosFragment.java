package com.example.clockin5.ui.empleados;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.clockin5.R;
import com.example.clockin5.modelo.Empleado;

import java.util.ArrayList;

public class EmpleadosFragment extends Fragment {

    private EmpleadosViewModel empleadosViewModel;

    private ListView lista;


    Adaptador adaptador;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        empleadosViewModel =
                ViewModelProviders.of(this).get(EmpleadosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cerrar, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        empleadosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        lista = container.findViewById(R.id.list);

        /*adaptador = new Adaptador(this.getActivity(), empleados);
        lista.setAdapter(adaptador);*/

        return root;
    }

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

            } else
                contenedor = (Contenedor) item.getTag();

            /*contenedor.nombre.setText(empleados.get(position).getNombre());
            contenedor.getEmail().setText(empleados.get(position).getIdEmp());*/

            return (item);
        }
    }

    static class Contenedor
    {
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
}
