package com.proyectox.shoppingcartweb.servicio;

import com.proyectox.shoppingcartweb.dao.CarritoDAO;
import com.proyectox.shoppingcartweb.dao.ProductoDAO;
import com.proyectox.shoppingcartweb.modelo.Carrito;
import com.proyectox.shoppingcartweb.modelo.Cliente;
import com.proyectox.shoppingcartweb.modelo.ItemCarrito;
import com.proyectox.shoppingcartweb.modelo.Producto;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class CarritoService {
	
	private final ProductoDAO productoDAO;
    private final CarritoDAO carritoDAO; // NUEVO: Añadir instancia de CarritoDAO

    // NUEVO: Modificar constructor para recibir ambos DAOs
    public CarritoService(ProductoDAO productoDAO, CarritoDAO carritoDAO) {
        this.productoDAO = productoDAO;
        this.carritoDAO = carritoDAO;
    }

    // Método para obtener el cliente logueado (si existe)
    private Cliente getClienteLogueado(HttpSession session) {
        return (Cliente) session.getAttribute("clienteLogueado");
    }

    // Obtiene/Crea el carrito en sesión (sin cambios)
    
    private List<ItemCarrito> getCarritoEnSesion(HttpSession session) {
    	@SuppressWarnings("unchecked")
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }
        return carrito;
    }

    // --- NUEVOS MÉTODOS ---

    /**
     * Carga el carrito desde la BD a la sesión cuando un usuario inicia sesión.
     * Si el usuario no tenía carrito en BD, se crea uno vacío.
     * @param session La HttpSession del usuario.
     */
    public void cargarCarritoDeBD(HttpSession session) {
        Cliente cliente = getClienteLogueado(session);
        if (cliente != null) {
            // Obtener o crear el registro del carrito en la BD para este cliente
            Carrito carritoBD = carritoDAO.obtenerOCrearCarritoPorClienteId(cliente.getId());
            if (carritoBD != null) {
                // Cargar los detalles del carrito desde la BD
                List<ItemCarrito> itemsDesdeBD = carritoDAO.obtenerDetallesPorCarritoId(carritoBD.getIdCarrito());
                // Guardar la lista cargada en la sesión
                session.setAttribute("carrito", itemsDesdeBD);
                // Opcional: Guardar el ID del carrito de BD en sesión para fácil acceso
                session.setAttribute("idCarritoBD", carritoBD.getIdCarrito());
            } else {
                 // Si hubo un error obteniendo/creando el carritoBD, asegurar que haya una lista vacía en sesión
                 session.setAttribute("carrito", new ArrayList<ItemCarrito>());
                 session.removeAttribute("idCarritoBD"); // Limpiar por si acaso
            }
        }
    }

    /**
     * Limpia el carrito de la sesión al cerrar sesión.
     * Opcionalmente, podría limpiar también la BD, pero usualmente se deja para la próxima sesión.
     * @param session La HttpSession del usuario.
     */
    public void limpiarCarritoDeSesion(HttpSession session) {
        session.removeAttribute("carrito");
        session.removeAttribute("idCarritoBD"); // Limpiar también el ID guardado
    }

    // --- MÉTODOS MODIFICADOS ---

    public void agregarProducto(HttpSession session, int idProducto) {
        // Lógica de sesión (igual que antes)
        Producto producto = productoDAO.obtenerPorId(idProducto);
        List<ItemCarrito> carritoSesion = getCarritoEnSesion(session);
        boolean existe = false;
        int cantidadAAgregar = 1; // Cantidad a sumar en BD

        for (ItemCarrito item : carritoSesion) {
            if (item.getProducto().getId() == idProducto) {
                item.setCantidad(item.getCantidad() + 1);
                existe = true;
                break; // Salimos del bucle una vez encontrado y actualizado
            }
        }
        if (!existe && producto != null) {
            carritoSesion.add(new ItemCarrito(producto, 1));
        }

        // NUEVO: Lógica de persistencia si el usuario está logueado
        Cliente cliente = getClienteLogueado(session);
        Integer idCarritoBD = (Integer) session.getAttribute("idCarritoBD"); // Recupera el ID del carrito de BD
        if (cliente != null && idCarritoBD != null && producto != null) {
            carritoDAO.agregarOActualizarItem(idCarritoBD, idProducto, cantidadAAgregar);
        }
    }

    public void eliminarProducto(HttpSession session, int idProducto) {
        // Lógica de sesión (igual que antes)
        List<ItemCarrito> carritoSesion = getCarritoEnSesion(session);
        carritoSesion.removeIf(item -> item.getProducto().getId() == idProducto);

        // NUEVO: Lógica de persistencia si el usuario está logueado
        Cliente cliente = getClienteLogueado(session);
         Integer idCarritoBD = (Integer) session.getAttribute("idCarritoBD");
        if (cliente != null && idCarritoBD != null) {
            carritoDAO.eliminarItem(idCarritoBD, idProducto);
        }
    }

    public void actualizarCantidad(HttpSession session, int idProducto, int cantidad) {
        // Lógica de sesión (igual que antes)
        List<ItemCarrito> carritoSesion = getCarritoEnSesion(session);
        boolean itemEliminado = false; // Bandera para saber si se eliminó o actualizó

        if (cantidad <= 0) {
            carritoSesion.removeIf(item -> item.getProducto().getId() == idProducto);
            itemEliminado = true;
        } else {
            for (ItemCarrito item : carritoSesion) {
                if (item.getProducto().getId() == idProducto) {
                    item.setCantidad(cantidad);
                    break;
                }
            }
        }

        // NUEVO: Lógica de persistencia si el usuario está logueado
        Cliente cliente = getClienteLogueado(session);
        Integer idCarritoBD = (Integer) session.getAttribute("idCarritoBD");
        if (cliente != null && idCarritoBD != null) {
             // Llama al método del DAO que maneja tanto actualización como eliminación si cantidad <= 0
            carritoDAO.actualizarCantidadItem(idCarritoBD, idProducto, cantidad);
        }
    }

     // Sin cambios, opera sobre el carrito en sesión actual
    public int getConteoItems(HttpSession session) {
        return getCarritoEnSesion(session).size();
    }
}
