package com.example.clockin5.modelo;

import java.util.Date;

public class RegistroEmpleados {

    Empleado emple;
    Date fechaAlta;
    Date fechaBaja;

    public RegistroEmpleados(Empleado emple, Date fechaAlta, Date fechaBaja) {
        this.emple = emple;
        this.fechaAlta = fechaAlta;
        this.fechaBaja = fechaBaja;
    }

    public Empleado getEmple() {
        return emple;
    }

    public void setEmple(Empleado emple) {
        this.emple = emple;
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
