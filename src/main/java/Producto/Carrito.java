package Producto;

import modelo.Usuario;
import dao.ProductoDAO;
import modelo.Factura;
import modelo.Producto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Clase para gestionar el carrito de compras de un usuario.
 *
 * Autor: José Sequeira
 */
public class Carrito {

    private List<Producto> productosEnCarrito;
    private ProductoDAO productoDAO;

    public Carrito() {
        productosEnCarrito = new ArrayList<>();
        productoDAO = new ProductoDAO();
    }

    /**
     * Agrega un producto al carrito.
     *
     * @param producto Producto a agregar.
     */
    public void agregarProducto(Producto producto) {
        for (Producto p : productosEnCarrito) {
            if (p.getCodigo().equals(producto.getCodigo())) {
                System.out.println("El producto ya está en el carrito.");
                return;
            }
        }
        productosEnCarrito.add(producto);
    }

    /**
     * Elimina un producto del carrito.
     *
     * @param producto Producto a eliminar.
     */
    public void eliminarProducto(Producto producto) {
        productosEnCarrito.remove(producto);
    }

    /**
     * Obtiene la lista de productos en el carrito.
     *
     * @return Lista de productos en el carrito.
     */
    public List<Producto> obtenerProductos() {
        return new ArrayList<>(productosEnCarrito);
    }

    /**
     * Calcula el total del carrito.
     *
     * @return Total del carrito.
     */
    public double calcularTotal() {
        return productosEnCarrito.stream()
                .mapToDouble(Producto::getPrecio)
                .sum();
    }

    /**
     * Realiza el pedido, actualiza el inventario y genera una factura.
     *
     * @param usuario Usuario que realiza el pedido.
     */
    public void realizarPedido(Usuario usuario) {
        if (productosEnCarrito.isEmpty()) {
            System.out.println("El carrito está vacío. No se puede realizar el pedido.");
            return;
        }

        try {
            // Actualizar el inventario en la base de datos
            for (Producto producto : productosEnCarrito) {
                int nuevaCantidad = producto.getCantidad() - 1;
                if (nuevaCantidad < 0) {
                    System.out.println("No hay suficiente stock para el producto: " + producto.getNombre());
                    return;
                }
                producto.setCantidad(nuevaCantidad);
                productoDAO.actualizarProducto(producto.getCodigo(), producto.getNombre(), nuevaCantidad, producto.getPrecio(), producto.getCategoria());
            }

            // Generar la factura
            String facturaId = UUID.randomUUID().toString(); // Generar un ID único para la factura
            Factura factura = new Factura(facturaId, usuario, productosEnCarrito, calcularTotal(), new Date());
            System.out.println("Factura generada:\n" + factura.generarContenidoFactura());

            // Enviar la factura por correo
            factura.generarFacturaComoTxt();

            // Vaciar carrito después de realizar el pedido
            vaciarCarrito();

            System.out.println("Pedido realizado con éxito. Factura enviada por correo a: " + usuario.getEmail());

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al realizar el pedido: " + e.getMessage());
        }
    }

    /**
     * Vacía el carrito.
     */
    public void vaciarCarrito() {
        productosEnCarrito.clear();
    }
}