package com.example.clockin5.modelo;

import java.util.Date;

public class Jornada  implements java.io.Serializable {


    private int idJornada;
    private int tipo;
    private String ubicacion;
    private String hora;
    private String nota;
    //private Set registroJornadas = new HashSet(0);

    public Jornada() {
    }


    public Jornada(int idJornada) {
        this.idJornada = idJornada;
    }

    public Jornada(int idJornada, int tipo, String ubicacion, String hora, String nota) {
        this.idJornada = idJornada;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.hora = hora;
        this.nota = nota;
    }

    /*public Jornada(int idJornada, Byte tipo, String ubicacion, Date hora, String nota, Set registroJornadas) {
        this.idJornada = idJornada;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.hora = hora;
        this.nota = nota;
        this.registroJornadas = registroJornadas;
    }*/

    public int getIdJornada() {
        return this.idJornada;
    }

    public void setIdJornada(int idJornada) {
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

    /*public Set getRegistroJornadas() {
        return this.registroJornadas;
    }

    public void setRegistroJornadas(Set registroJornadas) {
        this.registroJornadas = registroJornadas;
    }*/
}