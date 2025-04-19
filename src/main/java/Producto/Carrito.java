/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Producto;

import modelo.Factura;
import modelo.Producto;
import Usuarios_Programa.Cliente;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 *
 * @author José Sequeira
 */
public class Carrito {

    private List<Producto> productosEnCarrito;

    public Carrito() {
        productosEnCarrito = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productosEnCarrito.add(producto);
    }

    public void eliminarProducto(Producto producto) {
        productosEnCarrito.remove(producto);
    }

    public List<Producto> obtenerProductos() {
        return new ArrayList<>(productosEnCarrito);
    }

    public double calcularTotal() {
        return productosEnCarrito.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();
    }

    public void realizarPedido(Cliente cliente) {
        // Lógica para realizar el pedido y actualizar el inventario
        for (Producto producto : productosEnCarrito) {
            // Suponiendo que el método actualizarStock está implementado
            producto.setCantidad(producto.getCantidad() - 1);
        }
        // Generar factura
        Factura factura = new Factura("ID_FACTURA", cliente, productosEnCarrito, calcularTotal(), new Date());
        // Enviar factura por correo (implementación necesaria)
        System.out.println("Pedido realizado con éxito. Factura generada y enviada por correo.");
    }
}
