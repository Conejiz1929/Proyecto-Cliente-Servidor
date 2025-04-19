/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author José Sequeira
 */
import conexion.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Producto;

public class ProductoDAO {

    public void registrarProducto(String codigo, String nombre, int cantidad, double precio, int categoria) throws SQLException {
        String sql = "INSERT INTO productos (id_producto, nombre, precio, stock, id_categoria) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            stmt.setString(2, nombre);
            stmt.setDouble(3, precio);
            stmt.setInt(4, cantidad);
            stmt.setInt(5, categoria);
            stmt.executeUpdate();
        }
    }

    public boolean existeProducto(String codigo) throws SQLException {
        String sql = "SELECT COUNT(*) FROM productos WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public int eliminarProducto(String codigo) throws SQLException {
        String sql = "DELETE FROM productos WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            return stmt.executeUpdate();
        }
    }

    public List<Producto> obtenerProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.id_producto, p.nombre, p.precio, p.stock, c.nombre as categoria "
                + "FROM productos p JOIN categorias c ON p.id_categoria = c.id_categoria";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                productos.add(new Producto(
                        rs.getString("id_producto"),
                        rs.getString("nombre"),
                        rs.getInt("stock"),
                        rs.getDouble("precio"),
                        rs.getString("categoria")
                ));
            }
        }
        return productos;
    }

    public int actualizarProducto(String codigo, String nombre, int cantidad, double precio, int categoria) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, stock = ?, id_categoria = ? WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setDouble(2, precio);
            stmt.setInt(3, cantidad);
            stmt.setInt(4, categoria);
            stmt.setString(5, codigo);
            return stmt.executeUpdate();
        }
    }

    public Producto buscarPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT p.id_producto, p.nombre, p.precio, p.stock, c.nombre as categoria "
                + "FROM productos p JOIN categorias c ON p.id_categoria = c.id_categoria "
                + "WHERE p.id_producto = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Producto(
                            rs.getString("id_producto"),
                            rs.getString("nombre"),
                            rs.getInt("stock"),
                            rs.getDouble("precio"),
                            rs.getString("categoria")
                    );
                }
            }
        }
        return null;
    }

    public List<Producto> obtenerProductosConDescuento() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
