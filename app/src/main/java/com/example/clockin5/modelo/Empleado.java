package com.example.clockin5.modelo;

public class Empleado {

    String idEmp;
    String nombre;
    String apellido1;
    String apellido2;
    String dni;
    String imagen;
    boolean jefe;

    public Empleado(String idEmp, String nombre, String apellido1, String apellido2, String dni, String imagen, boolean jefe) {
        this.idEmp = idEmp;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dni = dni;
        this.imagen = imagen;
        this.jefe = jefe;
    }

    public String getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(String idEmp) {
        this.idEmp = idEmp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isJefe() {
        return jefe;
    }

    public void setJefe(boolean jefe) {
        this.jefe = jefe;
    }
}
