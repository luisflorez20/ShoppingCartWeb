package com.proyectox.shoppingcartweb.dao;

import com.proyectox.shoppingcartweb.modelo.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // Método para obtener todos los productos de la base de datos
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Recorremos cada fila del resultado
            while (rs.next()) {
                Producto p = new Producto(); // Creamos un producto vacío
                // Llenamos el objeto con los datos de la fila
                p.setId(rs.getInt("idproducto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getDouble("precio"));
                p.setImagen(rs.getString("imagen"));
                productos.add(p); // Añadimos el producto a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Un buen manejo de errores es importante
        }
        return productos;
    }

    // Método para obtener un producto específico por su ID
    public Producto obtenerPorId(int id) {
        Producto producto = null;
        String sql = "SELECT * FROM producto WHERE idproducto = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id); // Asignamos el ID al '?' del SQL
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = new Producto();
                    producto.setId(rs.getInt("idproducto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setPrecio(rs.getDouble("precio"));
                    producto.setImagen(rs.getString("imagen"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }
}
