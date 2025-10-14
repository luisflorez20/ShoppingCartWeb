package com.proyectox.shoppingcartweb.controlador;


import com.proyectox.shoppingcartweb.dao.ClienteDAO;
import com.proyectox.shoppingcartweb.dao.ProductoDAO;
import com.proyectox.shoppingcartweb.modelo.Cliente;
import com.proyectox.shoppingcartweb.modelo.ItemCarrito;
import com.proyectox.shoppingcartweb.modelo.Producto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Controlador")
public class Controlador extends HttpServlet {

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO(); // Añadir instancia de ClienteDAO

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            // Acciones de Usuario
            case "login":
                login(request, response);
                break;
            case "registrar":
                registrar(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            case "verLogin":
                request.getRequestDispatcher("login.jsp").forward(request, response);
                break;
            case "verRegistro":
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                break;

            // Acciones de Carrito
            case "agregar":
                agregarAlCarrito(request, response);
                break;
            case "eliminar":
                eliminarDelCarrito(request, response);
                break;
            case "actualizar":
                actualizarCantidad(request, response);
                break;
            case "verCarrito":
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
                break;
            default: // "listar"
                listarProductos(request, response);
                break;
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Cliente cliente = clienteDAO.validar(email, password);

        if (cliente != null) { // Si el cliente es válido
            HttpSession session = request.getSession();
            session.setAttribute("clienteLogueado", cliente);
            // Redirigimos a la página principal
            listarProductos(request, response);
        } else {
            // Si no es válido, lo devolvemos a la página de login
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Cliente cliente = new Cliente();
        cliente.setNombres(request.getParameter("nombres"));
        cliente.setApellidos(request.getParameter("apellidos"));
        cliente.setEmail(request.getParameter("email"));
        cliente.setPassword(request.getParameter("password"));

        clienteDAO.registrar(cliente);

        // Una vez registrado, lo mandamos a la página de login para que inicie sesión
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate(); // Invalida la sesión completa
        listarProductos(request, response);
    }

    // ... (El resto de los métodos para el carrito y listar productos permanecen igual)

    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Producto> productos = productoDAO.listar();
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private void agregarAlCarrito(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        Producto producto = productoDAO.obtenerPorId(idProducto);

        HttpSession session = request.getSession();
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new ArrayList<>();
        }

        boolean existe = false;
        for (ItemCarrito item : carrito) {
            if (item.getProducto().getId() == idProducto) {
                item.setCantidad(item.getCantidad() + 1);
                existe = true;
                break;
            }
        }

        if (!existe) {
            carrito.add(new ItemCarrito(producto, 1));
        }

        session.setAttribute("carrito", carrito);
        response.sendRedirect(request.getContextPath() + "/Controlador?accion=listar");
    }

    private void eliminarDelCarrito(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");

        if (carrito != null) {
            carrito.removeIf(item -> item.getProducto().getId() == idProducto);
        }

        response.sendRedirect(request.getContextPath() + "/Controlador?accion=verCarrito");
    }

    private void actualizarCantidad(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));

        HttpSession session = request.getSession();
        List<ItemCarrito> carrito = (List<ItemCarrito>) session.getAttribute("carrito");

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

        response.sendRedirect(request.getContextPath() + "/Controlador?accion=verCarrito");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}