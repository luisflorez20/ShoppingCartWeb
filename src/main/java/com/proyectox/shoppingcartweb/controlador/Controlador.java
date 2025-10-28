package com.proyectox.shoppingcartweb.controlador;


import com.proyectox.shoppingcartweb.dao.ClienteDAO; // Importar CienteDAO
import com.proyectox.shoppingcartweb.dao.ProductoDAO;
import com.proyectox.shoppingcartweb.modelo.Cliente; // Importar Cliente
import com.proyectox.shoppingcartweb.modelo.Producto;

import com.proyectox.shoppingcartweb.servicio.CarritoService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/Controlador")
public class Controlador extends HttpServlet {

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO(); // Añadir instancia de ClienteDAO
    private CarritoService carritoService; // Declaramos la instancia del servicio

    // Método init() para inicializar el servicio
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // Creamos el servicio pasándole el DAO que necesita
        this.carritoService = new CarritoService(productoDAO);
    }

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
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
                break;
            case "verRegistro":
                request.getRequestDispatcher("/WEB-INF/registro.jsp").forward(request, response);
                break;

            // Acciones de Carrito
            case "agregar":
                agregarAlCarrito(request, response);
                break;
            // Caso para la accion Ajax
            case "agregarAjax":
                agregarAlCarritoAjax(request, response);
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

    // --- Métodos del Carrito Refactorizados ---
    private void agregarAlCarrito(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();

        // Llama al servicio para hacer la lógica
        carritoService.agregarProducto(session, idProducto);

        // El controlador sigue manejando la redirección
        response.sendRedirect(request.getContextPath() + "/Controlador?accion=listar");
    }

    // Nuevo: Método para manejar la petición AJAX de agregar al carrito
    private void agregarAlCarritoAjax(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();

        // Llama al servicio para hacer la lógica

        carritoService.agregarProducto(session, idProducto);

        // Obtiene el conteo de servicio
        int nuevoConteo = carritoService.getConteoItems(session);

        // En lugar de redirigir, respondemos con el nuevo tamaño del carrito
        // El controlador sigue manejando la respuesta AJAX
        response.setContentType("text/plain"); // Indicamos que la respuesta es texto
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(nuevoConteo); // Enviamos solo el numero de items
        out.flush(); // Aseguramos que la respuesta se envíe
    }

    private void eliminarDelCarrito(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();

        // Llama al servicio para hacer la lógica
        carritoService.eliminarProducto(session, idProducto);

        // El controlador sigue manejando la redirección
        response.sendRedirect(request.getContextPath() + "/Controlador?accion=verCarrito");
    }

    private void actualizarCantidad(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        HttpSession session = request.getSession();

        // Llama al servicio para hacer la lógica
        carritoService.actualizarCantidad(session, idProducto, cantidad);

        // El controlador sigue manejando la redirección
        response.sendRedirect(request.getContextPath() + "/Controlador?accion=verCarrito");
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
            request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
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
        request.getRequestDispatcher("WEB-INF/login.jsp").forward(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate(); // Invalida la sesión completa
        listarProductos(request, response);
    }


    private void listarProductos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Producto> productos = productoDAO.listar();
        request.setAttribute("productos", productos);
        request.getRequestDispatcher("index.jsp").forward(request, response);
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