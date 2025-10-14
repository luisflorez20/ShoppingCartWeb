package com.proyectox.shoppingcartweb.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:postgresql://localhost:5432/almacen";
    private static final String USUARIO = "postgres";
    private static final String CONTRASENA = "donluis";

    public static Connection getConexion() throws SQLException {
        try {
            // Carga el driver de PostgreSQL
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            // Lanza un excepción si no se encuentra el driver
            throw new SQLException("Error: No se encontró el driver PostgreSQL", e);
        }
        // Retorna la conexión a la base de datos
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
