package com.example.clockin5.modelo;

import java.util.Date;

public class RegistroEmpleados {

    int idEmpleado;
    Date fechaAlta;
    Date fechaBaja;

    public RegistroEmpleados(int idEmpleado, Date fechaAlta, Date fechaBaja) {
        this.idEmpleado = idEmpleado;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public Date getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(Date fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
}
