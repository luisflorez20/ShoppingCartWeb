package com.proyectox.shoppingcartweb.controlador;


import com.proyectox.shoppingcartweb.dao.CarritoDAO;
import com.proyectox.shoppingcartweb.dao.ClienteDAO;
import com.proyectox.shoppingcartweb.dao.ProductoDAO;
import com.proyectox.shoppingcartweb.modelo.Cliente;
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
import java.util.ArrayList;
import java.util.List;


@WebServlet("/Controlador")
public class Controlador extends HttpServlet {
	// Declaramos nuestros DAOs y Servicios
    private ProductoDAO productoDAO;
    private ClienteDAO clienteDAO;
    private CarritoDAO carritoDAO;
    private CarritoService carritoService;

    // NUEVO: Método init() para inicializar DAOs y Servicios
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // Inicializamos los DAOs
        this.productoDAO = new ProductoDAO();
        this.clienteDAO = new ClienteDAO();
        // Inyectamos ProductoDAO en CarritoDAO
        this.carritoDAO = new CarritoDAO(this.productoDAO);
        // Inyectamos los DAOs necesarios en CarritoService
        this.carritoService = new CarritoService(this.productoDAO, this.carritoDAO);
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
                // Asumimos que carrito.jsp también está protegido en WEB-INF
                request.getRequestDispatcher("carrito.jsp").forward(request, response);
                break;
            default: // "listar"
                listarProductos(request, response);
                break;
        }
    }

    // --- Métodos de Usuario (Login/Logout) Actualizados ---

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Cliente cliente = clienteDAO.validar(email, password);

        if (cliente != null) { // Si el cliente es válido
            HttpSession session = request.getSession();
            session.setAttribute("clienteLogueado", cliente);

            // *** NUEVA LÍNEA ***
            // Cargar el carrito desde la BD (o crear uno) y ponerlo en la sesión
            carritoService.cargarCarritoDeBD(session);
            
            // Redirigimos a la página principal
            listarProductos(request, response);
        } else {
            // Si no es válido, lo devolvemos a la página de login
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // (Este método no necesita cambios, solo registra al usuario)
        Cliente cliente = new Cliente();
        cliente.setNombres(request.getParameter("nombres"));
        cliente.setApellidos(request.getParameter("apellidos"));
        cliente.setEmail(request.getParameter("email"));
        cliente.setPassword(request.getParameter("password"));

        clienteDAO.registrar(cliente);

        // Lo mandamos a la página de login para que inicie sesión
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // *** NUEVA LÍNEA ***
        // Limpiar el carrito de la sesión antes de invalidarla
        carritoService.limpiarCarritoDeSesion(session);

        session.invalidate(); // Invalida la sesión completa
        listarProductos(request, response);
    }

    // --- Métodos del Carrito (Refactorizados) ---
    // (Estos métodos ya llaman al servicio, como hicimos en el paso anterior)

    private void agregarAlCarrito(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        carritoService.agregarProducto(session, idProducto);
        response.sendRedirect(request.getContextPath() + "/Controlador?accion=listar");
    }

    private void agregarAlCarritoAjax(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        
        carritoService.agregarProducto(session, idProducto); // El servicio actualiza sesión y BD
        
        int nuevoConteo = carritoService.getConteoItems(session); // El servicio nos da el conteo
        
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(nuevoConteo);
        out.flush();
    }

    private void eliminarDelCarrito(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        HttpSession session = request.getSession();
        carritoService.eliminarProducto(session, idProducto);
        response.sendRedirect(request.getContextPath() + "/Controlador?accion=verCarrito");
    }

    private void actualizarCantidad(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        HttpSession session = request.getSession();
        carritoService.actualizarCantidad(session, idProducto, cantidad);
        response.sendRedirect(request.getContextPath() + "/Controlador?accion=verCarrito");
    }

    // --- Otros Métodos ---
    
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