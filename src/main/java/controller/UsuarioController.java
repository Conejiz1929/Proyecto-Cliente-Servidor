/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author José Sequeira
 */

import modelo.Usuario;
import dao.UsuarioDAO;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario autenticar(String email, String password, String rol) {
        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        
        if (usuario != null && 
            SeguridadUtil.verificarPassword(password, usuario.getPassword()) && 
            usuario.getRol().equals(rol)) {
            return usuario;
        }
        return null;
    }
    
    // Otros métodos CRUD para usuarios
}