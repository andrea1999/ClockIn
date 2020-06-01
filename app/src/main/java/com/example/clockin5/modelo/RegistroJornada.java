package com.example.clockin5.modelo;

import java.util.Date;

public class RegistroJornada  implements java.io.Serializable {


    private String fecha;
    private Empleado empleado;
    private Jornada jornada;

    public RegistroJornada() {
    }

    public RegistroJornada(String fecha, Empleado empleado, Jornada jornada) {
        this.fecha = fecha;
        this.empleado = empleado;
        this.jornada = jornada;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public Empleado getEmpleado() {
        return this.empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    public Jornada getJornada() {
        return this.jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }
}