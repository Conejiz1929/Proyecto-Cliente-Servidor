/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Conej
 */
public class Inventario {

    private List<Producto> productos;

    public Inventario() {
        this.productos = new ArrayList<>();
        cargarProductosPredeterminados();
    }

    public final void cargarProductosPredeterminados() {
        // Añadir algunos productos de ejemplo
        productos.add(new Producto("Laptop", "Portátil de alta performance", 1200.00, 10, "Electrónica"));
        productos.add(new Producto("Smartphone", "Teléfono inteligente último modelo", 800.00, 15, "Electrónica"));
        productos.add(new Producto("Auriculares", "Auriculares inalámbricos con noise cancelling", 250.00, 20, "Accesorios"));
        productos.add(new Producto("Tablet", "Tablet de 10 pulgadas", 500.00, 8, "Electrónica"));
        productos.add(new Producto("Smartwatch", "Reloj inteligente con monitor de salud", 300.00, 12, "Accesorios"));
    }

    public void agregarProducto(Producto producto) {
        if (producto != null) {
            productos.add(producto);
        }
    }

    public List<Producto> obtenerProductos() {
        return new ArrayList<>(productos); // Devuelve una copia para evitar modificaciones directas
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }

    public void actualizarStock(Producto producto, int cantidadNueva) {
        for (Producto p : productos) {
            if (p.equals(producto)) {
                p.setCantidad(cantidadNueva);
                break;
            }
        }
    }

    public List<Producto> filtrarPorCategoria(String categoria) {
        return productos.stream()
                .filter(p -> p.getCategoria().equals(categoria))
                .collect(Collectors.toList());
    }

    public List<Producto> filtrarPorPrecio(double precioMin, double precioMax) {
        return productos.stream()
                .filter(p -> p.getPrecio() >= precioMin && p.getPrecio() <= precioMax)
                .collect(Collectors.toList());
    }

    public List<Producto> filtrarPorDisponibilidad() {
        return productos.stream()
                .filter(p -> p.getCantidad() > 0)
                .collect(Collectors.toList());
    }

    public void alertarProductoAgotado() {
        productos.stream()
                .filter(p -> p.getCantidad() <= 5) // Umbral de alerta
                .forEach(p -> {
                    // Lógica de alerta (puede ser imprimir en consola, enviar email, etc.)
                    System.out.println("Alerta: El producto " + p.getNombre() + " está próximo a agotarse.");
                });
    }
}
