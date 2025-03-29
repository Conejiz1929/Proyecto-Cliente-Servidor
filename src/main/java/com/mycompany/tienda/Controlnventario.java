/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

/**
 *
 * @author José Sequeira
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controlnventario {

    private Map<String, Producto> inventario;
    private static final int LIMITE_BAJO_STOCK = 5; // Límite para alertar bajo stock

    public Controlnventario() {
        this.inventario = new HashMap<>();
    }

    // Actualizar existencias tras una compra
    public void actualizarExistencias(String codigo, int cantidadVendida) {
        if (inventario.containsKey(codigo)) {
            Producto producto = inventario.get(codigo);
            if (producto.getCantidad() >= cantidadVendida) {
                producto.setCantidad(producto.getCantidad() - cantidadVendida);
                System.out.println("Existencias actualizadas.");
                verificarBajoStock(producto);
            } else {
                System.out.println("Stock insuficiente para la venta.");
            }
        } else {
            System.out.println("Producto no encontrado en el inventario.");
        }
    }

    // Verificar si un producto está próximo a agotarse
    private void verificarBajoStock(Producto producto) {
        if (producto.getCantidad() <= LIMITE_BAJO_STOCK) {
            System.out.println("ALERTA: El producto " + producto.getNombre() + " está próximo a agotarse.");
        }
    }

    // Mostrar el inventario completo
    public void mostrarInventario() {
        System.out.println("Inventario actual:");
        for (Producto producto : inventario.values()) {
            System.out.println(producto);
        }
    }

    public void eliminarProducto(String codigo) {
        inventario.remove(codigo);
    }

    // Obtener todos los productos del inventario
    public List<Producto> obtenerProductos() {
        return new ArrayList<>(inventario.values()); // Devuelve una lista con los productos
    }

    public Inventario convertirAInventario() {
        Inventario inventarioObj = new Inventario();
        for (Producto producto : inventario.values()) {
            inventarioObj.agregarProducto(producto);
        }
        return inventarioObj;
    }

    public void registrarProducto(String codigo, String nombre, int cantidad, double precio, String categoria) {
        if (inventario.containsKey(codigo)) {
            System.out.println("El producto con el código " + codigo + " ya existe.");
        } else {
            Producto nuevoProducto = new Producto(codigo, nombre, cantidad, precio, categoria);
            inventario.put(codigo, nuevoProducto);
            System.out.println("Producto agregado exitosamente: " + nuevoProducto);
        }
    }
}
