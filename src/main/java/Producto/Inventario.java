/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Producto;

import modelo.Producto;
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

    // Cargar productos predeterminados al inventario
    public final void cargarProductosPredeterminados() {
        productos.add(new Producto("Laptop", "Portátil de alta performance", 120000, 10, "Electrónica"));
        productos.add(new Producto("Smartphone", "Teléfono inteligente último modelo", 80000, 15, "Electrónica"));
        productos.add(new Producto("Auriculares", "Auriculares inalámbricos con noise cancelling", 25000, 20, "Accesorios"));
        productos.add(new Producto("Tablet", "Tablet de 10 pulgadas", 50000, 8, "Electrónica"));
        productos.add(new Producto("Smartwatch", "Reloj inteligente con monitor de salud", 30000, 12, "Accesorios"));
    }

    // Agregar un nuevo producto al inventario
    public void agregarProducto(Producto producto) {
        if (producto != null) {
            productos.add(producto);
            System.out.println("Producto agregado al inventario: " + producto.getNombre());
        }
    }

    // Obtener todos los productos del inventario
    public List<Producto> obtenerProductos() {
        return new ArrayList<>(productos); // Devuelve una copia para evitar modificaciones directas
    }

    // Eliminar un producto del inventario
    public void eliminarProducto(Producto producto) {
        if (productos.remove(producto)) {
            System.out.println("Producto eliminado del inventario: " + producto.getNombre());
        } else {
            System.out.println("El producto no se encontró en el inventario.");
        }
    }

    // Actualizar el stock de un producto
    public void actualizarStock(String codigo, int cantidadNueva) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo)) {
                p.setCantidad(cantidadNueva);
                System.out.println("Stock actualizado para el producto: " + p.getNombre());
                alertarProductoAgotado(p);
                return;
            }
        }
        System.out.println("Producto no encontrado en el inventario.");
    }

    // Filtrar productos por categoría
    public List<Producto> filtrarPorCategoria(String categoria) {
        return productos.stream()
                .filter(p -> p.getCategoria().equalsIgnoreCase(categoria))
                .collect(Collectors.toList());
    }

    // Filtrar productos por rango de precios
    public List<Producto> filtrarPorPrecio(double precioMin, double precioMax) {
        return productos.stream()
                .filter(p -> p.getPrecio() >= precioMin && p.getPrecio() <= precioMax)
                .collect(Collectors.toList());
    }

    // Filtrar productos disponibles (stock > 0)
    public List<Producto> filtrarPorDisponibilidad() {
        return productos.stream()
                .filter(p -> p.getCantidad() > 0)
                .collect(Collectors.toList());
    }

    // Alertar si un producto está próximo a agotarse
    private void alertarProductoAgotado(Producto producto) {
        if (producto.getCantidad() <= 5) { // Umbral de alerta
            System.out.println("ALERTA: El producto " + producto.getNombre() + " está próximo a agotarse.");
        }
    }

    // Alertar todos los productos con bajo stock
    public void alertarProductosAgotados() {
        productos.stream()
                .filter(p -> p.getCantidad() <= 5) // Umbral de alerta
                .forEach(p -> System.out.println("ALERTA: El producto " + p.getNombre() + " está próximo a agotarse."));
    }
}
