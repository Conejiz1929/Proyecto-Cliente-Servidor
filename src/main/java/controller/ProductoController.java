package controller;

/**
 * Controlador para gestionar las operaciones relacionadas con productos. Se
 * conecta al DAO para interactuar con la base de datos.
 *
 * Autor: José Sequeira
 */
import dao.ProductoDAO;
import modelo.Producto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoController {

    private ProductoDAO productoDAO;

    public ProductoController() {
        this.productoDAO = new ProductoDAO();
    }

    // Registrar un nuevo producto
    public boolean registrarProducto(String codigo, String nombre, int cantidad, double precio, String categoria) {
        try {
            if (existeProducto(codigo)) {
                return false; // No registrar si el producto ya existe
            }
            productoDAO.registrarProducto(codigo, nombre, cantidad, precio, categoria);
            return true; // Producto registrado exitosamente
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Error al registrar
        }
    }

    // Verificar si un producto existe
    public boolean existeProducto(String codigo) {
        try {
            return productoDAO.existeProducto(codigo);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un producto por su código
    public boolean eliminarProducto(String codigo) {
        try {
            return productoDAO.eliminarProducto(codigo) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todos los productos
    public List<Producto> obtenerTodosProductos() {
        try {
            return productoDAO.obtenerProductos();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Actualizar un producto
    public boolean actualizarProducto(Producto producto) {
        try {
            return productoDAO.actualizarProducto(
                    producto.getCodigo(),
                    producto.getNombre(),
                    producto.getCantidad(),
                    producto.getPrecio(),
                    producto.getCategoria()
            ) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Buscar un producto por su código
    public Producto buscarProducto(String codigo) {
        try {
            return productoDAO.buscarPorCodigo(codigo);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Generar un reporte del inventario
    public String generarReporteInventario() {
        try {
            List<Producto> productos = productoDAO.obtenerProductos();

            StringBuilder reporte = new StringBuilder();
            reporte.append("=== REPORTE DE INVENTARIO ===\n\n");
            reporte.append(String.format("%-10s %-30s %-10s %-10s %-15s\n",
                    "Código", "Nombre", "Precio", "Stock", "Categoría"));
            reporte.append("-------------------------------------------------\n");

            int totalProductos = 0;
            int productosBajoStock = 0;
            double valorTotal = 0;

            for (Producto producto : productos) {
                reporte.append(String.format("%-10s %-30s $%-9.2f %-10d %-15s\n",
                        producto.getCodigo(),
                        producto.getNombre(),
                        producto.getPrecio(),
                        producto.getCantidad(),
                        producto.getCategoria()));

                totalProductos++;
                valorTotal += producto.getPrecio() * producto.getCantidad();

                if (producto.getCantidad() < 10) {
                    productosBajoStock++;
                }
            }

            reporte.append("\n=== RESUMEN ===\n");
            reporte.append(String.format("Total productos: %d\n", totalProductos));
            reporte.append(String.format("Productos con bajo stock (<10): %d\n", productosBajoStock));
            reporte.append(String.format("Valor total del inventario: $%.2f\n", valorTotal));

            return reporte.toString();

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al generar el reporte: " + e.getMessage();
        }
    }

    // Obtener productos destacados (con lógica pendiente de descuento en DAO)
    public List<Producto> obtenerProductosDestacados() {
        return productoDAO.obtenerProductosConDescuento();
    }
}
