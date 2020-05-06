package com.example.clockin5.modelo;

import java.util.Date;

public class Jornada {

    int idJornada;
    int tipo;
    String ubicacion;
    Date hora;
    String nota;

    public Jornada(int idJornada, int tipo, String ubicacion, Date hora, String nota) {
        this.idJornada = idJornada;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.hora = hora;
        this.nota = nota;
    }

    public int getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(int idJornada) {
        this.idJornada = idJornada;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
