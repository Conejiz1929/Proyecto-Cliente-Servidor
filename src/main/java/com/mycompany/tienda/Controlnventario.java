/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tienda;

/**
 *
 * @author José Sequeira
 */
import java.sql.*;
import javax.swing.JOptionPane;

public class Controlnventario {

    // Verifica si un producto ya existe en la base de datos
    public boolean existeProducto(String codigo) {
        String sql = "SELECT COUNT(*) FROM Productos WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Registra un nuevo producto en la base de datos
    public void registrarProducto(String codigo, String nombre, int cantidad, double precio, int idCategoria) {
        String sql = "INSERT INTO Productos (nombre, descripcion, precio, stock, id_categoria) VALUES (?, '', ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setDouble(2, precio);
            stmt.setInt(3, cantidad);
            stmt.setInt(4, idCategoria);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto agregado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Elimina un producto de la base de datos
    public void eliminarProducto(String codigo) {
        String sql = "DELETE FROM Productos WHERE id_producto = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
