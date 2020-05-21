package com.example.clockin5.modelo;

public class Usuario {

    Empleado emple;
    String email;
    String password;
    int pin;

    public Usuario(Empleado emple, String email, String password, int pin) {
        this.emple = emple;
        this.email = email;
        this.password = password;
        this.pin = pin;
    }

    public Empleado getEmple() {
        return emple;
    }

    public void setEmple(Empleado Emple) {
        this.emple = emple;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}
