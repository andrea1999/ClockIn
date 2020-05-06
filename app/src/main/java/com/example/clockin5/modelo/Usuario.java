package com.example.clockin5.modelo;

public class Usuario {

    int idEmp;
    String email;
    String password;
    int pin;

    public Usuario(int idEmp, String email, String password, int pin) {
        this.idEmp = idEmp;
        this.email = email;
        this.password = password;
        this.pin = pin;
    }

    public int getIdEmp() {
        return idEmp;
    }

    public void setIdEmp(int idEmp) {
        this.idEmp = idEmp;
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
