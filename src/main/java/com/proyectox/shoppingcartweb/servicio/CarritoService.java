package com.proyectox.shoppingcartweb.servicio;

import com.proyectox.shoppingcartweb.dao.ProductoDAO;
import com.proyectox.shoppingcartweb.modelo.ItemCarrito;
import com.proyectox.shoppingcartweb.modelo.Producto;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class CarritoService {

    public final ProductoDAO productoDAO;

    // Constructor que recibe el DAO (Inyección de Dependencias manual)
    public CarritoService(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    /**
     * Obtiene la lista de items del carrito desde la sesión.
     * Si no existe, crea una nueva lista vacía.
     * @param session La HttpSession del usuario.
     * @return La lista de ItemCarrito.
     */
    private List<ItemCarrito> getCarrito(HttpSession session) {
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito); // Importante: guardar la nueva lista de sesión
        }
        return carrito;
    }

    // Método para agregar producto
    public void agregarProducto(HttpSession session,  int idProducto) {
        Producto producto = productoDAO.obtenerPorId(idProducto);
        List<ItemCarrito> carrito = getCarrito(session);

        boolean existe = false;
        for (ItemCarrito item : carrito) {
            if (item.getProducto().getId() == idProducto) {
                item.setCantidad(item.getCantidad() + 1);
                existe = true;
                break;
            }
        }

        if (!existe && producto != null) { // Asegurarse que el producto no sea nullo
            carrito.add(new ItemCarrito(producto, 1));
        }

    }

    // Método para eliminar
    public void eliminarProducto(HttpSession session, int idProducto) {
        List<ItemCarrito> carrito = getCarrito(session); // Usa el método privado

        if (carrito != null) {
            carrito.removeIf(item -> item.getProducto().getId() == idProducto);
        }
    }

    // Método para actualiar
    public void actualizarCantidad(HttpSession session, int idProducto, int cantidad) {
        List<ItemCarrito> carrito = getCarrito(session);

        if (cantidad <= 0) {
            if (carrito != null) {
                carrito.removeIf(item -> item.getProducto().getId() == idProducto);
            }
        } else {
            if (carrito != null) {
                for (ItemCarrito item : carrito) {
                    if (item.getProducto().getId() == idProducto) {
                        item.setCantidad(cantidad);
                        break;
                    }
                }
            }
        }
        // No hay manejo de response aquí
    }

    // Método para obtener el conteo actual (util para AJAX)
    public int getConteoItems(HttpSession session) {
        // Contamos los items individuales, no la suma de cantidades
        return getCarrito(session).size();
        /* Si quisiéramos la suma total de unidades:
           return getCarrito(session).stream().mapToInt(ItemCarrito::getCantidad).sum();
        */
    }
}
