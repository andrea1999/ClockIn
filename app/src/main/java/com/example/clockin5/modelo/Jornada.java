package com.example.clockin5.modelo;

public class Jornada implements java.io.Serializable {

    private String idJornada;
    private int tipo;
    private String ubicacion;
    private String hora;
    private String nota;

    //constructor
    public Jornada(String idJornada, int tipo, String ubicacion, String hora, String nota) {
        this.idJornada = idJornada;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.hora = hora;
        this.nota = nota;
    }

    //getters y setters
    public String getIdJornada() {
        return this.idJornada;
    }

    public void setIdJornada(String idJornada) {
        this.idJornada = idJornada;
    }

    public int getTipo() {
        return this.tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return this.ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getHora() {
        return this.hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getNota() {
        return this.nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}