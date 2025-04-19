/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Login_Registro;

/**
 *
 * @author José Sequeira
 */
import modelo.Administrador;
import modelo.Usuario;
import java.util.HashMap;
import java.util.Map;

public class GestorUsuarios {

    private Map<String, Usuario> usuarios;
    private Map<String, Administrador> administradores;

    public GestorUsuarios() {
        usuarios = new HashMap<>();
        administradores = new HashMap<>();
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.put(usuario.getEmail(), usuario);
    }

    public void registrarAdministrador(Administrador administrador) {
        administradores.put(administrador.getEmail(), administrador);
    }

    public Usuario obtenerUsuario(String email) {
        if (usuarios.containsKey(email)) {
            return usuarios.get(email);
        } else if (administradores.containsKey(email)) {
            return administradores.get(email);
        }
        return null;
    }

    public boolean autenticarUsuario(String email, String password) {
        Usuario usuario = obtenerUsuario(email);
        return usuario != null && usuario.getPassword().equals(password);
    }
}
