package com.example.clockin5.modelo;

import java.util.HashSet;
import java.util.Set;

public class Empleado  implements java.io.Serializable {


    private String idEmp;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String dni;
    private String imagen;
    private boolean jefe;
    //private Set registroJornadas = new HashSet(0);
    //private RegistroEmpleados registroEmpleados;

    public Empleado() {
    }


    public Empleado(String idEmp, String nombre, String apellido1, String apellido2, String dni, String imagen, boolean jefe) {
        this.idEmp = idEmp;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dni = dni;
        this.imagen = imagen;
        this.jefe = jefe;
    }

    /*public Empleado(String idEmp, String nombre, String apellido1, String apellido2, String dni, String imagen, byte jefe, Set registroJornadas, RegistroEmpleados registroEmpleados) {
        this.idEmp = idEmp;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dni = dni;
        this.imagen = imagen;
        this.jefe = jefe;
        this.registroJornadas = registroJornadas;
        this.registroEmpleados = registroEmpleados;
    }*/

    public String getIdEmp() {
        return this.idEmp;
    }

    public void setIdEmp(String idEmp) {
        this.idEmp = idEmp;
    }
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido1() {
        return this.apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }
    public String getApellido2() {
        return this.apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    public String getImagen() {
        return this.imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public boolean getJefe() {
        return this.jefe;
    }

    public void setJefe(boolean jefe) {
        this.jefe = jefe;
    }

    /*public Set getRegistroJornadas() {
        return this.registroJornadas;
    }

    public void setRegistroJornadas(Set registroJornadas) {
        this.registroJornadas = registroJornadas;
    }
    public RegistroEmpleados getRegistroEmpleados() {
        return this.registroEmpleados;
    }

    public void setRegistroEmpleados(RegistroEmpleados registroEmpleados) {
        this.registroEmpleados = registroEmpleados;
    }*/

}