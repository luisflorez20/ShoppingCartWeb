package com.proyectox.shoppingcartweb.modelo;

import java.sql.Timestamp;

public class Carrito {

    private int idCarrito;
    private int idCliente;
    private Timestamp fechaCreacion;
    // Podríamos añadir aquí una lista de DetalleCarrito si quisiéramos cargarlo todo junto,
    // pero por ahora lo mantendremos simple y manejaremos los detalles por separado.

    public  Carrito() {
    }

    public Carrito(int idCarrito, int idCliente, Timestamp fechaCreacion) {
        this.idCarrito = idCarrito;
        this.idCliente = idCliente;
        this.fechaCreacion = fechaCreacion;
    }

    // --- Getters y Setters ---

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
