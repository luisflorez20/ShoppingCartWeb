package com.proyectox.shoppingcartweb.dao;

import com.proyectox.shoppingcartweb.modelo.Carrito;
import com.proyectox.shoppingcartweb.modelo.ItemCarrito; // Necesitamos ItemCarrito pra la info completa
import com.proyectox.shoppingcartweb.modelo.Producto; // Necesitamos Producto para construir ItemCarrito
import org.postgresql.core.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAO {

    private final ProductoDAO productoDAO; // Necesitamos acceso a los datos de producto
    public CarritoDAO(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    /**
     * Busca el carrito activo de un cliente. Si no existe, lo crea.
     * @param idCliente ID del cliente.
     * @return El objeto Carrito del cliente.
     */
    public Carrito obtenerOCrearCarritoPorCliente(int idCliente) {
        Carrito carrito = null;
        String sqlSelect = "SELECT idcarrito, idcliente, fechacreacion FROM carrito WHERE idcliente = ?";
        String sqlInsert = "INSERT INTO carrito (idcliente) VALUES (?) RETURNING idcarrito, idcliente, fechacreacion";

        try (Connection conn = Conexion.getConexion()) {
            // Intentar seleccionar primero
            try (PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect)) {
                stmtSelect.setInt(1, idCliente);
                try (ResultSet rs = stmtSelect.executeQuery()) {
                    if (rs.next()) {
                        carrito = new Carrito(rs.getInt("idcarrito"), rs.getInt("idcliente"), rs.getTimestamp("fechacreacion"));
                    }
                }
            }

            // Si no se encontró, insertar
            if (carrito == null) {
                try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                    stmtInsert.setInt(1, idCliente);
                    try (ResultSet rs = stmtInsert.executeQuery()) {
                        if (rs.next()) { // Usar RETURNING para obtener los datos insertados
                            carrito = new Carrito(rs.getInt("idcarrito"), rs.getInt("idcliente"), rs.getTimestamp("fechacreacion"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
        return carrito;
    }

    /**
     * Obtiene todos los items (producto y cantidad) de un carrito específico.
     * Combina datos de detalle_carrito y producto.
     * @param idCarrito ID del carrito.
     * @return Lista de ItemCarrito (similar a la que usamos en sesión).
     */
    public List<ItemCarrito> obtenerDetallesPorCarritoId(int idCarrito) {
        List<ItemCarrito> items = new ArrayList<>();
        // Unimos detalle_carrito con producto para obtener toda la info necesaria
        String sql = "SELECT dc.idproducto, dc.cantidad, p.nombre, p.descripcion, p.precio, p.imagen" +
                     "FROM detalle_carrito dc JOIN producto p ON dc.idproducto = p.idproducto" +
                     "WHERE dc.idcarrito = ?";

        try (Connection conn = Conexion.getConexion();
        PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, idCarrito);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Construimos el objeto Producto
                    Producto p = new Producto();
                    p.setId(rs.getInt("idproducto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setPrecio(rs.getDouble("precio"));
                    p.setImagen(rs.getString("imagen"));
                    // Obtenemos la cantidad
                    int cantidad = (rs.getInt("cantidad"));
                    // Creamos el ItemCarrito y lo añadimos a la lista
                    items.add(new ItemCarrito(p, cantidad));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Agrega un producto al carrito o actualiza su cantidad si ya existe.
     * Usa INSERT ... ON CONFLICT (característica de PostgreSQL) para simplificar.
     * @param idCarrito ID del carrito.
     * @param idProducto ID del producto.
     * @param cantidadAAgregar La cantidad a sumar (normalmente 1).
     */
    public void agregarOActualizarItem(int idCarrito, int idProducto, int cantidadAAgregar) {
        // SQL específico de PostgreSQL: Intenta insertar, si ya existe (por la UNIQUE constraint), actualiza.
        String sql = "INSERT INTO detalle_carrito (idcarrito, idproducto, cantidad) VALUES (?, ?, ?)" +
                "ON CONFLICT (idcarrito, idproducto) DO UPDATE SET cantidad = detalle_carrito.cantidad + EXCLUDED.cantidad";

        try (Connection conn = Conexion.getConexion();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCarrito);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidadAAgregar); // La cantidad inicial o la que se suma
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Actualiza la cantidad de un producto específico en el carrito.
     * Si la cantidad es <= 0, elimina el item.
     * @param idCarrito ID del carrito.
     * @param idProducto ID del producto.
     * @param nuevaCantidad La nueva cantidad total.
     */
    public void actualizarCantidadItem(int idCarrito,int idProducto, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            eliminarItem(idCarrito, idProducto);
        } else {
            String sql = "UPDATE detalle_carrito SET cantidad = ? WHERE idcarrito = ? AND idproducto = ?";
            try (Connection conn = Conexion.getConexion();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, nuevaCantidad);
                stmt.setInt(2, idCarrito);
                stmt.setInt(2, idProducto);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Elimina un producto específico del carrito.
     * @param idCarrito ID del carrito.
     * @param idProducto ID del producto a eliminar.
     */
    public void eliminarItem(int idCarrito, int idProducto) {
        String sql = "DELETE FROM detalle_carrito WHERE idcarrito = ? AND idproducto = ?";

        try (Connection conn = Conexion.getConexion();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCarrito);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina todos los productos (detalles) de un carrito.
     * @param idCarrito ID del carrito a limpiar.
     */
    public void limpiarCarrito(int idCarrito) {
        String sql = "DELETE FROM detalle_carrito WHERE idcarrito = ?";

        try (Connection conn = Conexion.getConexion();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCarrito);
            stmt.executeUpdate();

        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }
}






















