package dao;

/**
 * Clase para interactuar con la base de datos de productos.
 *
 * @author José Sequeira
 */
import conexion.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;

public class ProductoDAO {

    // Método para registrar un nuevo producto
    public void registrarProducto(String codigo, String nombre, int cantidad, double precio, String categoria) throws SQLException {
        String sql = "INSERT INTO Productos (codigo, nombre, cantidad, precio, categoria) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            stmt.setString(2, nombre);
            stmt.setInt(3, cantidad);
            stmt.setDouble(4, precio);
            stmt.setString(5, categoria);
            stmt.executeUpdate();
        }
    }

    // Método para verificar si un producto existe por su código
    public boolean existeProducto(String codigo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Productos WHERE codigo = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    // Método para eliminar un producto por su código
    public int eliminarProducto(String codigo) throws SQLException {
        String sql = "DELETE FROM Productos WHERE codigo = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            return stmt.executeUpdate();
        }
    }

    // Método para obtener todos los productos
    public List<Producto> obtenerProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT codigo, nombre, precio, cantidad, categoria, fecha_creacion FROM Productos";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getString("codigo"),
                        rs.getString("nombre"),
                        rs.getInt("cantidad"),
                        rs.getDouble("precio"),
                        rs.getString("categoria"),
                        rs.getTimestamp("fecha_creacion")
                ));
            }
        }
        return productos;
    }

    // Método para actualizar un producto por su código
    public int actualizarProducto(String codigo, String nombre, int cantidad, double precio, String categoria) throws SQLException {
        String sql = "UPDATE Productos SET nombre = ?, precio = ?, cantidad = ?, categoria = ? WHERE codigo = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setDouble(2, precio);
            stmt.setInt(3, cantidad);
            stmt.setString(4, categoria);
            stmt.setString(5, codigo);
            return stmt.executeUpdate();
        }
    }

    // Método para buscar un producto por su código
    public Producto buscarPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT codigo, nombre, precio, cantidad, categoria, fecha_creacion FROM Productos WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Producto(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getInt("cantidad"),
                            rs.getDouble("precio"),
                            rs.getString("categoria"),
                            rs.getTimestamp("fecha_creacion")
                    );
                }
            }
        }
        return null;
    }

    // Método para obtener productos con descuento (pendiente de lógica específica)
    public List<Producto> obtenerProductosConDescuento() {
        // Aquí se puede implementar la lógica para obtener productos con descuento
        return new ArrayList<>();
    }
}
