package com.proyectox.shoppingcartweb.modelo;

public class Cliente {
    private int id;
    private String nombres;
    private String apellidos;
    private String email;
    private String password;

    public Cliente() {
    }

    // Getters y setters para todos los campos...


    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getNombres() {

        return nombres;
    }

    public void setNombres(String nombres) {

        this.nombres = nombres;
    }

    public String getApellidos() {

        return apellidos;
    }

    public void setApellidos(String apellidos) {

        this.apellidos = apellidos;
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
}
