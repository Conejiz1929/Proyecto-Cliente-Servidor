/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

/**
 *
 * @author Conej
 */
class Producto {

    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidad;
    private String categoria; // Nuevo campo para la categoría

    public Producto(String nombre, String descripcion, double precio, int cantidad, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.categoria = categoria; // Inicializar la categoría
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() { // Nuevo método getCategoria
        return categoria;
    }

    public void setCategoria(String categoria) { // Nuevo método setCategoria
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Producto producto = (Producto) obj;
        return nombre.equals(producto.nombre);
    }

    @Override
    public String toString() {
        return nombre + " - $" + precio + " (Stock: " + cantidad + ")";
    }
}
