/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author José Sequeira
 */
import modelo.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import conexion.DatabaseConnection;
import modelo.Administrador;

public class UsuarioDAO {

    public static boolean registrarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (id_usuario, nombre, email, password, tipo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getId());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getPassword());
            stmt.setString(5, usuario.getRol());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Usuario autenticarUsuario(String email, String password) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getString("id_usuario"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("rol")
                    );
                }
            }
        }
        return null;
    }
    
    public Usuario buscarPorEmail(String email) {
    String sql = "SELECT * FROM usuarios WHERE email = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Usuario(
                rs.getString("id"),
                rs.getString("nombre"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("rol")
            );
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


    public static boolean registrarAdministrador(Administrador administrador) throws SQLException {
        String sql = "INSERT INTO usuarios (id_usuario, nombre, email, password, tipo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, administrador.getId());
            stmt.setString(2, administrador.getNombre());
            stmt.setString(3, administrador.getEmail());
            stmt.setString(4, administrador.getPassword());
            stmt.setString(5, administrador.getRol());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeUsuario(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public List<Usuario> obtenerTodosUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                usuarios.add(new Usuario(
                        rs.getString("id_usuario"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("tipo")
                ));
            }
        }
        return usuarios;
    }

    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, password = ?, tipo = ? WHERE id_usuario = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getPassword());
            stmt.setString(4, usuario.getRol());
            stmt.setString(5, usuario.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean eliminarUsuario(String idUsuario) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id_usuario = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, idUsuario);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean correoExiste(String correo) {
        String sql = "SELECT id FROM usuarios WHERE correo = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar correo: " + e.getMessage());
            return false;
        }
    }
}
