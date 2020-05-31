package com.example.clockin5;

import com.example.clockin5.modelo.Empleado;
import com.example.clockin5.modelo.Jornada;
import com.example.clockin5.modelo.RegistroEmpleados;
import com.example.clockin5.modelo.RegistroJornada;

import java.util.ArrayList;
import java.util.Date;

public class Gestor {

    public ArrayList<RegistroEmpleados> getRegistroEmpleadosArrayList() {
        return registroEmpleadosArrayList;
    }

    private static ArrayList<Empleado> empleadoArrayList;
    private static ArrayList<Jornada> jornadaArrayList;
    private static ArrayList<RegistroJornada> registroJornadasArrayList;
    private static ArrayList<RegistroEmpleados> registroEmpleadosArrayList;

    public Gestor(){
        empleadoArrayList = new ArrayList<Empleado>();
        jornadaArrayList = new ArrayList<Jornada>();
        registroJornadasArrayList = new ArrayList<RegistroJornada>();

        iniciarEmpleados();
        iniciarJornadas();
        iniciarRegistroJornada();
    }

    public void iniciarEmpleados(){
        Empleado e1 = new Empleado("aaaaaa", "Steve", "Rogers", "Capitán América", "00141697V", "tjgdfkll", false);
        Empleado e2 = new Empleado("bbbbbb", "Tony", "Stark", "Ironman", "dgtgre", "40932530M", true);
        Empleado e3 = new Empleado("cccccc", "Peter", "Parker", "Spiderman", "01156312X", "fvbghngu", false);
        Empleado e4 = new Empleado("dddddd", "Natasha", "Romanoff", "Viuda Negra", "69616472A", "fvbghngu", false);

        empleadoArrayList.add(e1);
        empleadoArrayList.add(e2);
        empleadoArrayList.add(e3);
        empleadoArrayList.add(e4);
    }

    public void  iniciarJornadas(){
        Jornada j1 = new Jornada(1, 0, "", new Date(1590742547),"Hola");
        Jornada j2 = new Jornada(2, 1, "", new Date(1590764147),"");
        Jornada j3 = new Jornada(3, 0, "", new Date(1590828947),"Coche");
        Jornada j4 = new Jornada(4, 1, "", new Date(1590846947),"");
        Jornada j5 = new Jornada(5, 0, "", new Date(1590872147),"Hola");

        jornadaArrayList.add(j1);
        jornadaArrayList.add(j2);
        jornadaArrayList.add(j3);
        jornadaArrayList.add(j4);
        jornadaArrayList.add(j5);
    }

    public void  iniciarRegistroJornada(){
        RegistroJornada rj1 = new RegistroJornada(new Date(1590742547), empleadoArrayList.get(0), jornadaArrayList.get(0));
        RegistroJornada rj2 = new RegistroJornada(new Date(1590764147), empleadoArrayList.get(0), jornadaArrayList.get(1));
        RegistroJornada rj3 = new RegistroJornada(new Date(1590828947), empleadoArrayList.get(1), jornadaArrayList.get(2));
        RegistroJornada rj4 = new RegistroJornada(new Date(1590846947), empleadoArrayList.get(1), jornadaArrayList.get(3));
        RegistroJornada rj5 = new RegistroJornada(new Date(1590872147), empleadoArrayList.get(2), jornadaArrayList.get(4));

        registroJornadasArrayList.add(rj1);
        registroJornadasArrayList.add(rj2);
        registroJornadasArrayList.add(rj3);
        registroJornadasArrayList.add(rj4);
        registroJornadasArrayList.add(rj5);
    }

    public void iniciarRegistroEmpleados(){

    }

    public ArrayList<Empleado> getEmpleadoArrayList() {
        return empleadoArrayList;
    }

    public ArrayList<Jornada> getJornadaArrayList() {
        return jornadaArrayList;
    }

    public ArrayList<RegistroJornada> getRegistroJornadasArrayList() {
        return registroJornadasArrayList;
    }

    public Empleado buscarEmpleadoId(String id){
        for (int i = 0; i < empleadoArrayList.size(); i++) {
            if (empleadoArrayList.get(i).getIdEmp().equals(id)){
                return empleadoArrayList.get(i);
            }
        }
        return null;
    }
}
