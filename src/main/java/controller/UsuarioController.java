/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import modelo.Usuario;
import dao.UsuarioDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author José Sequeira
 */
public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // Método para autenticar un usuario
    public Usuario autenticar(String email, String password, String rol) {
        Usuario usuario = usuarioDAO.buscarPorEmail(email);

        if (usuario != null
                && usuario.getPassword().equals(password)
                && // Comparación directa de contraseñas
                usuario.getRol().equals(rol)) {
            return usuario;
        }
        return null;
    }

    // Método para registrar un nuevo usuario
    public boolean registrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null || usuario.getPassword() == null) {
            throw new IllegalArgumentException("El usuario, su email y contraseña no pueden ser nulos.");
        }
        try {
            return usuarioDAO.registrarUsuario(usuario);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Método para actualizar un usuario existente
    public boolean actualizarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("El usuario y su ID no pueden ser nulos.");
        }
        try {
            return usuarioDAO.actualizarUsuario(usuario);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Método para eliminar un usuario por su ID
    public boolean eliminarUsuario(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser nulo o vacío.");
        }
        try {
            return usuarioDAO.eliminarUsuario(id);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // Método para obtener un usuario por su ID
    public Usuario obtenerUsuarioPorId(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("El ID no puede ser nulo o vacío.");
        }
        return usuarioDAO.buscarPorId(id);
    }

    // Método para obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() throws SQLException {
        return usuarioDAO.obtenerTodosUsuarios();
    }
}
