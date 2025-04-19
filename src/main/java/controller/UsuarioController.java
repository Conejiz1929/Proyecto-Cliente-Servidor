/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import modelo.Usuario;
import dao.UsuarioDAO;
import java.sql.SQLException;
import java.util.List;

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
        
        if (usuario != null && 
            usuario.getPassword().equals(password) && // Comparación directa de contraseñas
            usuario.getRol().equals(rol)) {
            return usuario;
        }
        return null;
    }

    // Método para registrar un nuevo usuario
    public boolean registrarUsuario(Usuario usuario) throws SQLException {
        return usuarioDAO.registrarUsuario(usuario);
    }

    // Método para actualizar un usuario existente
    public boolean actualizarUsuario(Usuario usuario) throws SQLException {
        return usuarioDAO.actualizarUsuario(usuario);
    }

    // Método para eliminar un usuario por su ID
    public boolean eliminarUsuario(String id) throws SQLException {
        return usuarioDAO.eliminarUsuario(id);
    }

    // Método para obtener un usuario por su ID
    public Usuario obtenerUsuarioPorId(String id) {
        return usuarioDAO.buscarPorId(id);
    }

    // Método para obtener todos los usuarios
    public List<Usuario> obtenerTodosUsuarios() throws SQLException {
        return usuarioDAO.obtenerTodosUsuarios();
    }
}