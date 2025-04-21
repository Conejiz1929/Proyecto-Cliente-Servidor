package modelo;

import java.sql.Timestamp;

/**
 * Clase modelo para representar un producto en el sistema. Representa la
 * estructura de la tabla "Productos" en la base de datos.
 *
 * @author Conej
 */
public class Producto {

    private String codigo; // Código único del producto
    private String nombre; // Nombre del producto
    private int cantidad; // Cantidad disponible
    private double precio; // Precio del producto
    private String categoria; // Categoría del producto
    private Timestamp fechaCreacion; // Fecha de creación del producto

    // Constructor con todos los campos
    public Producto(String codigo, String nombre, int cantidad, double precio, String categoria, Timestamp fechaCreacion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.categoria = categoria;
        this.fechaCreacion = fechaCreacion;
    }

    // Constructor sin fecha de creación (útil para nuevos productos)
    public Producto(String codigo, String nombre, int cantidad, double precio, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.categoria = categoria;
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Producto{"
                + "codigo='" + codigo + '\''
                + ", nombre='" + nombre + '\''
                + ", cantidad=" + cantidad
                + ", precio=" + precio
                + ", categoria='" + categoria + '\''
                + ", fechaCreacion=" + fechaCreacion
                + '}';
    }
}
