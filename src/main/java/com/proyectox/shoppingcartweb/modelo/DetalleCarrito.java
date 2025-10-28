package com.proyectox.shoppingcartweb.modelo;

public class DetalleCarrito {
    private int idDetalle;
    private int idCarrito; // Referencia al carrito al que pertenece
    private int idProducto; // Referencia al producto
    private int cantidad;

    // Podríamos añadir aquí el objeto Producto si quisiéramos más detalles,
    // pero mantener solo los IDs es más eficiente para la persistencia.

    public DetalleCarrito() {
    }

    public DetalleCarrito(int idDetalle, int idCarrito, int idProducto, int cantidad) {
        this.idDetalle = idDetalle;
        this.idCarrito = idCarrito;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }

    // --- Getters y Setters ---

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
