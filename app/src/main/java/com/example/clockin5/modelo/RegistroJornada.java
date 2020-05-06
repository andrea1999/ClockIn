package com.example.clockin5.modelo;

import java.util.Date;

public class RegistroJornada {

    int idEmpleado;
    int idJornada;
    Date fecha;

    public RegistroJornada(int idEmpleado, int idJornada, Date fecha) {
        this.idEmpleado = idEmpleado;
        this.idJornada = idJornada;
        this.fecha = fecha;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public int getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(int idJornada) {
        this.idJornada = idJornada;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
