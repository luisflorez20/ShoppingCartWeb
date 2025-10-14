package com.proyectox.shoppingcartweb.dao;

import com.proyectox.shoppingcartweb.modelo.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {
    /**
     * Válida si un cliente existe en la BD con el email y password proporcionados.
     * @param email El email del cliente.
     * @param password La constrasena del cliente.
     * @return El objeto Cliente si es valido, de lo contrario null
     */

    public Cliente validar(String email, String password) {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE email = ? AND password = ?";

        try(Connection conn = Conexion.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("idcliente"));
                    cliente.setNombres(rs.getString("nombres"));
                    cliente.setApellidos(rs.getString("apellidos"));
                    cliente.setEmail(rs.getString("email"));
                    // No recuperamos el password por seguridad, ya lo validamos
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace(); // Manejo de errores
        }
        return cliente;
    }

    /**
     * Registra un nuevo cliente en la base de datos.
     * @param cliente El objeto Cliente con los datos a registrar.
     */

    public void registrar(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombres, apellidos, email) VALUES (?, ?, ?)";

        try(Connection conn = Conexion.getConexion();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombres());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getPassword());
            stmt.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace(); // Manejo de errores
        }
    }
}
