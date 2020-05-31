package com.example.clockin5.modelo;

import java.util.Date;

public class RegistroEmpleados  implements java.io.Serializable {


    private String empleadoIdEmp;
    private Empleado empleado;
    private Date fechaAlta;
    private Date fechaBaja;

    public RegistroEmpleados() {

    }


    public RegistroEmpleados(Empleado empleado, Date fechaAlta) {
        this.empleado = empleado;
        this.fechaAlta = fechaAlta;
    }
    public RegistroEmpleados(Empleado empleado, Date fechaAlta, Date fechaBaja) {
        this.empleado = empleado;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
    }

    public String getEmpleadoIdEmp() {
        return this.empleadoIdEmp;
    }

    public void setEmpleadoIdEmp(String empleadoIdEmp) {
        this.empleadoIdEmp = empleadoIdEmp;
    }
    public Empleado getEmpleado() {
        return this.empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    public Date getFechaAlta() {
        return this.fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public Date getFechaBaja() {
        return this.fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }




}

