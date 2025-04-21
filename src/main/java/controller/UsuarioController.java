package controller;

import dao.UsuarioDAO;
import modelo.Usuario;

import java.sql.SQLException;
import java.util.List;

public class UsuarioController {

    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String email, String password) {
        try {
            return usuarioDAO.autenticarUsuario(email, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean registrarUsuario(Usuario usuario) {
        try {
            return usuarioDAO.registrarUsuario(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Usuario> obtenerTodosLosUsuarios() {
        try {
            return usuarioDAO.obtenerTodosUsuarios();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean actualizarUsuario(Usuario usuario) {
        try {
            return usuarioDAO.actualizarUsuario(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarUsuario(String id) {
        try {
            return usuarioDAO.eliminarUsuario(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
