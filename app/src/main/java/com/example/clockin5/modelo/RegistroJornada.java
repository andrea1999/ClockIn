package com.example.clockin5.modelo;

import java.util.Date;

public class RegistroJornada {

    Empleado emple;
    Jornada jornada;
    Date fecha;

    public RegistroJornada(Empleado emple, Jornada jornada, Date fecha) {
        this.emple = emple;
        this.jornada = jornada;
        this.fecha = fecha;
    }

    public Empleado getEmple() {
        return emple;
    }

    public void setEmple(Empleado Emple) {
        this.emple = emple;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
